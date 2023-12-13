package com.foreign.trade.service;

import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInfoService.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:37:00
 **/
public interface GoodsInfoService {
    int deleteByPrimaryKey(Integer[] goodsId);

    int deleteByCategoryId(Integer[] categoryId);

    int insert(GoodsInfo goodsInfo);

    GoodsInfo selectByPrimaryKey(Integer goodsId);

    List<GoodsInfo> selectByCategoryId(Integer categoryId);

    GoodsInfo selectByName(@Param("goodsName") String goodsName);

    int updateByPrimaryKey(GoodsInfo goodsInfo);

    int update(GoodsInfo goodsInfo);

    List<Map<String, Object>> getGoodsWithCategoryInfo(PageQueryUtil pageQueryUtil);

    List<GoodsInfo> findGoodsInfoList(PageQueryUtil pageQueryUtil);

    List<GoodsInfo> findGoodsInfoByCategory(PageQueryUtil pageQueryUtil);

    List<GoodsInfo> findGoodsInfoBySearch(PageQueryUtil pageQueryUtil);

    PageResult getGoodsWithCategoryPage(PageQueryUtil pageQueryUtil);

    PageResult getGoodsPage(PageQueryUtil pageQueryUtil);

    PageResult getCategoryGoodsPage(Integer goodsCategoryId, PageQueryUtil pageQueryUtil);

    PageResult searchGoods(PageQueryUtil pageQueryUtil);

    int getTotalGoods(PageQueryUtil pageQueryUtil);

    int getTotalGoodsByCategory(Integer goodsCategoryId, PageQueryUtil pageQueryUtil);

    int getTotalGoodsBySearch(PageQueryUtil pageQueryUtil);


}
