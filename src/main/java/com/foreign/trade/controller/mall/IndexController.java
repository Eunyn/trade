package com.foreign.trade.controller.mall;

import cn.hutool.captcha.ShearCaptcha;
import com.foreign.trade.config.Constants;
import com.foreign.trade.config.MailProperties;
import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.service.EmailService;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.service.GoodsInquiryService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.PatternUtil;
import com.foreign.trade.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource
    GoodsCategoryService goodsCategoryService;

    @Resource
    GoodsInfoService goodsInfoService;

    @Resource
    private GoodsInquiryService goodsInquiryService;

    @Resource
    private EmailService emailService;

    @GetMapping({"","/index"})
    public String indexPage(HttpServletRequest request) {
        // 默认显示第一个标签内的商品
//        List<GoodsCategory> categoryList = goodsCategoryService.select();
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("limit", 6);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<GoodsInfo> goodsInfoList = goodsInfoService.findGoodsInfoList(pageQueryUtil);
        request.setAttribute("products", goodsInfoList);
        params.put("page", 1);
        params.put("limit", 3);
        PageQueryUtil pageQueryUtil2 = new PageQueryUtil(params);
        List<GoodsInfo> goodsInfoList2 = goodsInfoService.findGoodsInfoList(pageQueryUtil2);
        request.setAttribute("goods", goodsInfoList2);
//        logger.info("GoodsInfoList2: {}", goodsInfoList2);

        return "mall/index";
    }

    @GetMapping("/search")
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

    @PostMapping("/inquiry")
    @ResponseBody
    public CompletableFuture<Result> inquiryAsync(HttpSession session, @RequestBody GoodsInquiry goodsInquiry, @RequestParam("verifyCode") String verifyCode) {
        return CompletableFuture.supplyAsync(()->inquiry(session, goodsInquiry, verifyCode));
    }

    public Result inquiry(HttpSession session, @RequestBody GoodsInquiry goodsInquiry, @RequestParam("verifyCode") String verifyCode) {
        if (goodsInquiry == null || goodsInquiry.getGoodsName() == null) {
            return new Result(404, "Data is null.");
        }
        if (!PatternUtil.isEmail(goodsInquiry.getEmail())) {
            return new Result(404, "The email format is not correct.");
        }

        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("mallVerifyCode");
        if (shearCaptcha == null) {
            session.setAttribute("errorMas", "VerifyCode is null");
            return new Result(404, "VerifyCode is null");
        }
        if (!shearCaptcha.getCode().equals(verifyCode)) {
            session.setAttribute("errorMsg", "VerifyCode error");
            return new Result(404, "VerifyCode error");
        }

//        logger.info("Inquiry: {}", goodsInquiry);
        goodsInquiry.setCreateTime(new Date());

        String email = goodsInquiry.getEmail();
        String subject = "Inquiry: " + goodsInquiry.getGoodsName();
        String message = getInquiryGoodsInfo(goodsInquiry);
        try {
            Boolean sendResult = emailService.sendEmailAsync(email, subject, message).get();
            logger.info("sendResult: {}", sendResult);
        } catch (MessagingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Result(404, "Send fail.");
        }

        int ret = goodsInquiryService.inquiry(goodsInquiry);
        if (ret == 0)
            return new Result(404, "Send fail2.");

        return new Result(200, "success");
    }

    @GetMapping("/about")
    public String toAboutPage(HttpServletRequest request) {

        return "mall/about";
    }

    public String getInquiryGoodsInfo(GoodsInquiry goodsInquiry) {
        return "Inquiry Information：" +
                "<br>产品名称：" + goodsInquiry.getGoodsName() +
                "<br>客户姓名：" + goodsInquiry.getYourName() +
                "<br>公司名称：" + goodsInquiry.getCompanyName() +
                "<br>客户地址：" + goodsInquiry.getAddress() +
                "<br>客户电话：" + goodsInquiry.getPhone() +
                "<br>客户传真：" + goodsInquiry.getFax() +
                "<br>客户邮箱：" + goodsInquiry.getEmail() +
                "<br>附加信息：" + goodsInquiry.getMessage();
    }
}