package com.foreign.trade.service.impl;

import com.foreign.trade.dao.GoodsCategoryMapper;
import com.foreign.trade.entity.GoodsAdmin;
import com.foreign.trade.entity.GoodsCategory;
import com.foreign.trade.service.GoodsAdminService;
import com.foreign.trade.service.GoodsCategoryService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCategoryServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:48:00
 **/
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer[] categoryIds) {
        return goodsCategoryMapper.deleteByPrimaryKey(categoryIds);
    }

    @Override
    public int insert(GoodsCategory record) {
        return goodsCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(GoodsCategory record) {
        return 0;
    }

    @Override
    public GoodsCategory selectByPrimaryKey(Integer categoryId) {
        return goodsCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public GoodsCategory selectByCategoryName(String categoryName) {
        return goodsCategoryMapper.selectByCategoryName(categoryName);
    }

    @Override
    public int updateByPrimaryKey(GoodsCategory record) {
        return goodsCategoryMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageQueryUtil) {
        return goodsCategoryMapper.findGoodsCategoryList(pageQueryUtil);
    }

    @Override
    public PageResult getGoodsCategoryPage(PageQueryUtil pageQueryUtil) {
        List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.findGoodsCategoryList(pageQueryUtil);
        int totalCategory = goodsCategoryMapper.getTotalCategory(pageQueryUtil);
        PageResult pageResult = new PageResult(totalCategory, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), goodsCategoryList);

        return pageResult;
    }

    @Override
    public int getTotalCategory(PageQueryUtil pageQueryUtil) {
        return goodsCategoryMapper.getTotalCategory(pageQueryUtil);
    }


    @Override
    public List<GoodsCategory> select() {
        return goodsCategoryMapper.select();
    }
}
