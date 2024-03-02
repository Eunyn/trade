package com.foreign.trade.controller.admin;

import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import com.foreign.trade.util.Result;
import com.foreign.trade.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCategoryController.java
 * @Description: TODO
 * @CreateTime: 2023/11/29 10:39:00
 **/
@Controller
@RequestMapping("/admin")
public class GoodsCategoryController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private GoodsInfoService goodsInfoService;

    private final Logger logger = LoggerFactory.getLogger(GoodsCategoryController.class);

    @GetMapping("/categories")
    public String listForSelect(HttpServletRequest request) {
        request.setAttribute("path", "categories");

        return "admin/category";
    }

    @GetMapping("/categories/list")
    @ResponseBody
    public Result categoriesList(@RequestParam Map<String, Object> params) {
        if (!StringUtils.hasLength((String) params.get("page")) || !StringUtils.hasLength((String) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult goodsCategoryPage = goodsCategoryService.getGoodsCategoryPage(pageQueryUtil);

        return ResultGenerator.genSuccessResult(goodsCategoryPage);
    }

    @PostMapping("/insertCategory")
    @ResponseBody
    public Result addCategory(@RequestBody GoodsCategory goodsCategory) {
        if (goodsCategory == null || goodsCategory.getCategoryName() == null) {
            return ResultGenerator.genFailResult("Data is null");
        }
        GoodsCategory category = goodsCategoryService.selectByCategoryName(goodsCategory.getCategoryName());
        if (category != null)
            return ResultGenerator.genFailResult("It already exists");

        goodsCategory.setCreateTime(new Date());
        goodsCategoryService.insert(goodsCategory);

        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/deleteCategory")
    @ResponseBody
    public Result deleteCategory(@RequestBody Integer[] categoryIds) {
        if (categoryIds.length < 1) {
            return ResultGenerator.genFailResult("Data is not exist");
        }

        // 删除分类
        int i = goodsCategoryService.deleteByPrimaryKey(categoryIds);

        // 删除分类下的商品
//        int j = goodsInfoService.deleteByCategoryId(categoryIds);
//        for (Integer categoryId : categoryIds) {
//            List<GoodsInfo> goodsInfoList = goodsInfoService.selectByCategoryId(categoryId);
//            for (GoodsInfo goodsInfo : goodsInfoList) {
//                goodsInfo.g
//            }
//        }
        if (i != 0 )
            return ResultGenerator.genSuccessResult("Delete Success");

        return ResultGenerator.genFailResult("Delete fail");
    }

    @PostMapping("/updateCategory")
    @ResponseBody
    public Result updateCategory(@RequestBody GoodsCategory goodsCategory) {
        if (goodsCategory == null)
            return ResultGenerator.genFailResult("Data is null");
        logger.info("goodsCategory: {}", goodsCategory);
        int i = goodsCategoryService.updateByPrimaryKey(goodsCategory);
        if (i == 0)
            return ResultGenerator.genFailResult("Update fail");
        return ResultGenerator.genSuccessResult();
    }
}
