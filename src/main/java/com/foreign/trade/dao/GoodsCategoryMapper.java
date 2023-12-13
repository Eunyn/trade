package com.foreign.trade.dao;

import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCategoryMapper.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:15:00
 **/
public interface GoodsCategoryMapper {
    int deleteByPrimaryKey(Integer[] categoryId);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory  record);

    GoodsCategory selectByPrimaryKey(Integer categoryId);

    GoodsCategory selectByCategoryName(String categoryName);

    int updateByPrimaryKey(GoodsCategory record);

    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageQueryUtil);

    List<GoodsCategory> select();

    int getTotalCategory(PageQueryUtil pageQueryUtil);
}
