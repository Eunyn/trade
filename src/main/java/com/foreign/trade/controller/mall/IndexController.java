package com.foreign.trade.controller.mall;

import cn.hutool.captcha.ShearCaptcha;
import com.foreign.trade.config.Constants;
import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.service.EmailService;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.service.GoodsInquiryService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: IndexController.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 22:26:00
 **/
@Controller
public class IndexController {

    final private Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final Map<String, Integer> inquiryCount = new HashMap<>();

    private final Map<String, Long> inquiryIntervalTime = new HashMap<>();

    @Resource
    GoodsCategoryService goodsCategoryService;

    @Resource
    GoodsInfoService goodsInfoService;

    @Resource
    private GoodsInquiryService goodsInquiryService;

    @Resource
    private EmailService emailService;

    @GetMapping({"", "/mall/index"})
    public String indexPage(HttpServletRequest request) {
        // 首页默认显示数据库内前 12 条商品

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("limit", 12);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<GoodsInfo> goodsInfoList = goodsInfoService.findGoodsInfoList(pageQueryUtil);
        List<GoodsInfo> carousels = new ArrayList<>();
        List<GoodsInfo> middleProducts = new ArrayList<>();
        List<GoodsInfo> bottomProducts = new ArrayList<>();

        int size = goodsInfoList.size();
        for (int i = 0; i < 3 && i < size; i++) {
            carousels.add(goodsInfoList.get(i));
        }
        for (int i = 3; i < 9 && i < size; i++) {
            middleProducts.add(goodsInfoList.get(i));
        }
        for (int i = 9; i < 12 && i < size; i++) {
            bottomProducts.add(goodsInfoList.get(i));
        }

        request.setAttribute("carousels", carousels);
        request.setAttribute("products", middleProducts);
        request.setAttribute("goods", bottomProducts);
        request.setAttribute("indexPage", "index");

        return "mall/index";
    }

    @GetMapping("/mall/search")
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (!StringUtils.hasLength((String) params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT); // 默认每页显示8条
        params.put("order by", "new"); // 默认按照上传时间倒序排序
        String keyword = "";
        if (params.containsKey("keyword") && StringUtils.hasLength((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = goodsInfoService.searchGoods(pageQueryUtil);
        request.setAttribute("pageResult", pageResult);

        List<GoodsCategory> select = goodsCategoryService.select();
        request.setAttribute("categories", select);

        return "mall/search";
    }

    @PostMapping("/mall/inquiry")
    @ResponseBody
    public CompletableFuture<Result> inquiryAsync(HttpServletRequest request, @RequestBody GoodsInquiry goodsInquiry, @RequestParam("verifyCode") String verifyCode) {
        request.setAttribute("sendResult", goodsInquiry.getGoodsName());
        return CompletableFuture.supplyAsync(() -> inquiry(request, goodsInquiry, verifyCode));
    }

    public Result inquiry(HttpServletRequest request, @RequestBody GoodsInquiry goodsInquiry, @RequestParam("verifyCode") String verifyCode) {
        if (goodsInquiry == null || goodsInquiry.getGoodsName() == null) {
            return new Result(500, "Data is null.");
        }
        if (!Constants.isEmail(goodsInquiry.getEmail())) {
            return new Result(500, "The email format is not correct.");
        }

        ShearCaptcha shearCaptcha = (ShearCaptcha) request.getSession().getAttribute("mallVerifyCode");
        if (shearCaptcha == null) {
            request.getSession().setAttribute("errorMsg", "VerifyCode is null");
            return new Result(500, "VerifyCode is null");
        }
        if (!shearCaptcha.getCode().equals(verifyCode)) {
            request.getSession().setAttribute("errorMsg", "VerifyCode error");
            return new Result(500, "VerifyCode error");
        }

        // 限制 INQUIRY 太过频繁用户，默认 1 分钟三次
        String ipAddress = request.getRemoteAddr();
        if (isRateLimited(ipAddress)) {
            return new Result(500, "INQUIRY is too frequent, please try again later.");
        }
        updateTimes(ipAddress);

        goodsInquiry.setCreateTime(new Date());

        String email = goodsInquiry.getEmail();
        String subject = "Inquiry: " + goodsInquiry.getGoodsName();
        String message = getInquiryGoodsInfo(goodsInquiry);
        try {
            emailService.sendEmailAsync(email, subject, message).get();
        } catch (MessagingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            logger.info("### Send Fail=====");
            return new Result(500, "Email Send fail.");
        }

        int ret = goodsInquiryService.inquiry(goodsInquiry);
        if (ret == 0)
            return new Result(500, "Store the record fail.");

        return new Result(200, "success");
    }

    @GetMapping("/mall/about")
    public String toAboutPage(HttpServletRequest request) {

        return "mall/about";
    }

    private String getInquiryGoodsInfo(GoodsInquiry goodsInquiry) {
        String quantity = goodsInquiry.getQuantity() != null ? "" + goodsInquiry.getQuantity() : "";
        return "Inquiry Information：" +
                "<br>产品名称：" + goodsInquiry.getGoodsName() +
                "<br>订购数量：" + quantity +
                "<br>客户姓名：" + goodsInquiry.getYourName() +
                "<br>公司名称：" + goodsInquiry.getCompanyName() +
                "<br>客户地址：" + goodsInquiry.getAddress() +
                "<br>客户电话：" + goodsInquiry.getPhone() +
                "<br>客户传真：" + goodsInquiry.getFax() +
                "<br>客户邮箱：" + goodsInquiry.getEmail() +
                "<br>附加信息：" + goodsInquiry.getMessage();
    }

    private boolean isRateLimited(String ip) {
        int count = inquiryCount.getOrDefault(ip, 0);
        long startTime = inquiryIntervalTime.getOrDefault(ip, 0L);
        long currTime = System.currentTimeMillis();
        long interval = (currTime - startTime) / 1000; // 转换成秒数

        return count >= Constants.INQUIRY_RATE && interval <= Constants.INTERVAL_TIME;
    }

    private void updateTimes(String ip) {
        int count = inquiryCount.getOrDefault(ip, 0);
        if (count == 0) {
            inquiryIntervalTime.put(ip, System.currentTimeMillis());
        } else {
            Long currTime = System.currentTimeMillis();
            Long startTime = inquiryIntervalTime.get(ip);
            long interval = (currTime - startTime) / 1000;

            if (count >= Constants.INQUIRY_RATE || interval > Constants.INTERVAL_TIME) {
                inquiryCount.put(ip, 0);
                inquiryIntervalTime.put(ip, System.currentTimeMillis());
                count = 0;
            }
        }

        inquiryCount.put(ip, count + 1);
    }
}
