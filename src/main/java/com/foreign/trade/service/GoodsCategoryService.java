package com.foreign.trade.service;

import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;

import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCategoryService.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:37:00
 **/
public interface GoodsCategoryService {
    int deleteByPrimaryKey(Integer[] categoryIds);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory  record);

    GoodsCategory selectByPrimaryKey(Integer categoryId);

    GoodsCategory selectByCategoryName(String categoryName);

    int updateByPrimaryKey(GoodsCategory record);

    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageQueryUtil);

    PageResult getGoodsCategoryPage(PageQueryUtil pageQueryUtil);

    int getTotalCategory(PageQueryUtil pageQueryUtil);

    List<GoodsCategory> select();
}
