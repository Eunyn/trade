package com.foreign.trade.controller.mall;

import com.foreign.trade.config.Constants;
import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsDeatilController.java
 * @Description: TODO
 * @CreateTime: 2023/12/3 20:51:00
 **/
@Controller
@RequestMapping("/mall")
public class GoodsDetailController {

    private final Logger logger = LoggerFactory.getLogger(GoodsDetailController.class);

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/products")
    public String showProducts(HttpServletRequest request, @RequestParam Map<String, Object> params) {
        if (params.isEmpty()) {
            params = new HashMap<>();
            params.put("page", 1);
        } else {
            int page = Integer.parseInt((String) params.get("page"));
            params.put("page", page);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);

        List<GoodsCategory> select = goodsCategoryService.select();
        request.setAttribute("categories", select);

        PageQueryUtil queryUtil = new PageQueryUtil(params);
        PageResult pageResult = goodsInfoService.getGoodsPage(queryUtil);

        request.setAttribute("pageResult", pageResult);

        return "mall/product_all";
    }

    @GetMapping("/products/category")
    public String categoryProduct(HttpServletRequest request, @RequestParam Map<String, Object> params) {
        int goodsCategoryId = 1;
        if (params.isEmpty()) {
            params = new HashMap<>();
            params.put("page", 1);
        } else if (!StringUtils.hasLength((String) params.get("goodsCategoryId"))) {
            params.put("page", 1);
            params.put("goodsCategoryId", 1);
        } else if (!StringUtils.hasLength((String) params.get("page"))) {
            params.put("page", 1);
        }

        if (StringUtils.hasLength((String) params.get("goodsCategoryId"))) {
            goodsCategoryId = Integer.parseInt((String) params.get("goodsCategoryId"));
            request.setAttribute("goodsCategoryId", goodsCategoryId);
            params.put("goodsCategoryId", goodsCategoryId);

            GoodsCategory goodsCategory = goodsCategoryService.selectByPrimaryKey(goodsCategoryId);
            if (goodsCategory != null) {
                request.setAttribute("currCategory", goodsCategory.getCategoryName());
            }
        }

        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        PageResult pageResult = goodsInfoService.getCategoryGoodsPage(goodsCategoryId, pageQueryUtil);
        request.setAttribute("pageResult", pageResult);

//        logger.info("totalProducts: {}", goodsInfoService.getTotalGoodsByCategory(goodsCategoryId, pageQueryUtil));

        List<GoodsCategory> select = goodsCategoryService.select();
        request.setAttribute("categories", select);

        return "mall/product";
    }

    @GetMapping("/products/{goodsId}")
    public String productDetail(HttpServletRequest request, @PathVariable Integer goodsId) {
        List<GoodsCategory> select = goodsCategoryService.select();
        request.setAttribute("categories", select);

        GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(goodsId);
        request.setAttribute("details", goodsInfo);

        return "mall/product_detail";
    }
}
