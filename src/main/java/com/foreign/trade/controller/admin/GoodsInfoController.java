package com.foreign.trade.controller.admin;

import com.foreign.trade.config.Constants;
import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.service.GoodsInquiryService;
import com.foreign.trade.service.RedisService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInfoController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 16:00:00
 **/
@Controller
@RequestMapping("/admin")
public class GoodsInfoController {

    private final Logger logger = LoggerFactory.getLogger(GoodsInfoController.class);

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private GoodsInquiryService goodsInquiryService;

    @Resource
    private RedisService redisService;

    @GetMapping("/goods")
    public String toGoodsPage(HttpServletRequest request) {
        request.setAttribute("path", "goods");

        return "admin/goods";
    }

    @GetMapping("/goods/list")
    @ResponseBody
    public Result goodsList(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasLength((String) params.get("page")) || !StringUtils.hasLength((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = goodsInfoService.getGoodsWithCategoryPage(pageQueryUtil);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/goods/edit")
    public String goodsEdit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.select();
        request.setAttribute("categories", goodsCategoryList);

        return "admin/goods_edit";
    }

    @PostMapping("/goods/save")
    @ResponseBody
    public Result saveGoods(@RequestBody GoodsInfo goodsInfo) {
        if (!StringUtils.hasLength(goodsInfo.getGoodsName())
                || !StringUtils.hasLength(goodsInfo.getGoodsCoverImg())
                || goodsInfo.getGoodsCategoryId() == null) {
            return ResultGenerator.genFailResult("商品信息不全！");
        }

        String goodsName = goodsInfo.getGoodsName();
        GoodsInfo goods = goodsInfoService.selectByName(goodsName);
        if (goods != null) {
            return new Result(500, "商品名称重复！");
        }

        String[] images = goodsInfo.getGoodsCoverImg().split(",");
        String coverImg = images[0];
        Date createTime = parseTime(coverImg);
        goodsInfo.setCreateTime(createTime);
        if (images.length > 1) {
            String img = goodsInfo.getGoodsCoverImg().substring(coverImg.length() + 1);
            goodsInfo.setGoodsSubImg(img);
        }
        goodsInfo.setGoodsCoverImg(coverImg);
        int result = goodsInfoService.insert(goodsInfo);
        if (result == 0) {
            return ResultGenerator.genFailResult("保存失败");
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/goods/update")
    @ResponseBody
    public Result updateGoods(@RequestBody GoodsInfo goodsInfo) {
        if (!StringUtils.hasLength(goodsInfo.getGoodsName())
                || !StringUtils.hasLength(goodsInfo.getGoodsCoverImg())
                || goodsInfo.getGoodsCategoryId() == null) {
            return ResultGenerator.genFailResult("商品信息不全或有误！");
        }
        String[] images = goodsInfo.getGoodsCoverImg().split(",");

        if (images.length > 1) {
            String img = goodsInfo.getGoodsCoverImg().substring(images[0].length() + 1);
            goodsInfo.setGoodsSubImg(img);
        }
        goodsInfo.setGoodsCoverImg(images[0]);
        int i = goodsInfoService.updateByPrimaryKey(goodsInfo);
        if (i == 0) {
            return ResultGenerator.genFailResult("更新失败");
        }

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String editGoods(HttpServletRequest request, @PathVariable("goodsId") Integer goodsId) {
        request.setAttribute("path", "edit");
        GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(goodsId);
        request.setAttribute("goods", goodsInfo);
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.select();
        request.setAttribute("categories", goodsCategoryList);

        return "admin/goods_edit";
    }

    @PutMapping("/goods/delete")
    @ResponseBody
    public Result deleteGoods(@RequestBody Integer[] goodsIds) {
        if (goodsIds == null || goodsIds.length < 1)
            return ResultGenerator.genFailResult("Data is not exist");

        deleteRedisKey(goodsIds);
        deleteUploadImg(goodsIds);

        int i = goodsInfoService.deleteByPrimaryKey(goodsIds);
        if (i == 0)
            return ResultGenerator.genFailResult("Delete Fail");

        return ResultGenerator.genSuccessResult("Delete Success");
    }

    @GetMapping("/inquiry")
    public String inquiryHistory(HttpServletRequest request) {
        request.setAttribute("path", "inquiry");

        return "admin/inquiry";
    }

    @GetMapping("/inquiry/list")
    @ResponseBody
    public Result inquiryHistoryList(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasLength((String) params.get("page")) || !StringUtils.hasLength((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("获取分页参数失败");
        }

        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = goodsInquiryService.getInquiryHistoryPage(pageQueryUtil);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/inquiry/detail/{inquiryId}")
    @ResponseBody
    public Result inquiryDetail(@PathVariable("inquiryId") Integer inquiryId) {
        GoodsInquiry goodsInquiry = goodsInquiryService.getInquiryById(inquiryId);
        logger.info("查询信息为：{}", goodsInquiry);
        if (goodsInquiry == null) {
            return ResultGenerator.genFailResult("查看失败");
        }

        return ResultGenerator.genSuccessResult(goodsInquiry);
    }

    @PutMapping("/inquiry/delete")
    @ResponseBody
    public Result deleteInquiry(@RequestBody Integer[] inquiryIds) {
        if (inquiryIds == null || inquiryIds.length < 1) {
            return ResultGenerator.genFailResult("请选择要删除的数据");
        }

        int ret = goodsInquiryService.deleteInquiry(inquiryIds);
        if (ret == 0)
            return ResultGenerator.genFailResult("删除失败");

        return ResultGenerator.genSuccessResult("删除成功");
    }

    private void deleteUploadImg(Integer[] goodsIds) {
        for (Integer goodsId : goodsIds) {
            GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(goodsId);
            // 删除商品详情图
            deleteDetailImg(goodsInfo.getGoodsDetails());
            // 删除商品主图
            String imagePath = goodsInfo.getGoodsCoverImg();
            if (imagePath != null && !imagePath.isEmpty()) {
                String[] imgPathPart = imagePath.split("/");
                imagePath = imgPathPart[3];
                String imageLocalPath = Constants.FILE_UPLOAD_DIC_MAIN + imagePath;
                logger.info("imageLocalPath: {}", imageLocalPath);
                File imgFile = new File(imageLocalPath);
                if (imgFile.exists() && imgFile.isFile()) {
                    boolean delete = imgFile.delete();
                }
            }

            // 删除商品主图的子图
            deleteCoverSubImg(goodsInfo.getGoodsSubImg());
        }
    }

    private void deleteDetailImg(String detailContents) {
        if (!StringUtils.hasLength(detailContents))
            return;

        Pattern pattern = Pattern.compile("(?i)src\\s*=\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(detailContents);
        while (matcher.find()) {
            String group = matcher.group(1);
            String imagePath = Constants.FILE_UPLOAD_DIC_DETAILS + group.split("/")[3];
            File imgFile = new File(imagePath);
            if (imgFile.exists() && imgFile.isFile()) {
                boolean delete = imgFile.delete();
            }
        }
    }

    private void deleteCoverSubImg(String images) {
        if (!StringUtils.hasLength(images)) {
            return;
        }
        String[] split = images.split(",");
        for (String imagePath : split) {
            String[] imgPathPart = imagePath.split("/");
            imagePath = imgPathPart[3];
            String imageLocalPath = Constants.FILE_UPLOAD_DIC_MAIN + imagePath;
            File imgFile = new File(imageLocalPath);
            if (imgFile.exists() && imgFile.isFile()) {
                boolean delete = imgFile.delete();
            }
        }
    }

    private Date parseTime(String imgPath) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
        String[] split = imgPath.split("/");
        String time = split[3].split("\\.")[0];
        Date resultTime = new Date();
        try {
            resultTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTime;
    }

    private void deleteRedisKey(Integer[] goodsId) {
        for (Integer id : goodsId) {
            GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(id);
            if (goodsInfo != null) {
                redisService.deleteKeyByProduct(goodsInfo.getGoodsName());
            }
        }
    }
}
