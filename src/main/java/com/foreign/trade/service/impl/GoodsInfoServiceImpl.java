package com.foreign.trade.service.impl;

import com.foreign.trade.dao.GoodsCategoryMapper;
import com.foreign.trade.dao.GoodsInfoMapper;
import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.service.GoodsInfoService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInfoServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:49:00
 **/
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer[] goodsId) {
        return goodsInfoMapper.deleteByPrimaryKey(goodsId);
    }

    @Override
    public int deleteByCategoryId(Integer[] categoryId) {
        return goodsInfoMapper.deleteByCategoryId(categoryId);
    }

    @Override
    public int insert(GoodsInfo goodsInfo) {

        return goodsInfoMapper.insert(goodsInfo);
    }

    @Override
    public GoodsInfo selectByPrimaryKey(Integer goodsId) {
        return goodsInfoMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public List<GoodsInfo> selectByCategoryId(Integer categoryId) {
        return goodsInfoMapper.selectByCategoryId(categoryId);
    }

    @Override
    public GoodsInfo selectByName(String goodsName) {
        return goodsInfoMapper.selectByName(goodsName);
    }

    @Override
    public int updateByPrimaryKey(GoodsInfo goodsInfo) {
        return goodsInfoMapper.updateByPrimaryKey(goodsInfo);
    }

    @Override
    public int update(GoodsInfo goodsInfo) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> getGoodsWithCategoryInfo(PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.getGoodsWithCategoryInfo(pageQueryUtil);
    }

    @Override
    public List<GoodsInfo> findGoodsInfoList(PageQueryUtil pageQueryUtil) {
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.findGoodsInfoList(pageQueryUtil);
        return goodsInfoList;
    }

    @Override
    public List<GoodsInfo> findGoodsInfoByCategory(PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.findGoodsInfoByCategory(pageQueryUtil);
    }

    @Override
    public List<GoodsInfo> findGoodsInfoBySearch(PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.findGoodsInfoBySearch(pageQueryUtil);
    }

    @Override
    public PageResult getGoodsWithCategoryPage(PageQueryUtil pageQueryUtil) {
        List<Map<String, Object>> goodsWithCategoryInfo = goodsInfoMapper.getGoodsWithCategoryInfo(pageQueryUtil);
        int total = goodsInfoMapper.getTotalGoods(pageQueryUtil);
        PageResult pageResult = new PageResult(total, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), goodsWithCategoryInfo);

        return pageResult;
    }

    @Override
    public PageResult getGoodsPage(PageQueryUtil pageQueryUtil) {
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.findGoodsInfoList(pageQueryUtil);
        int totalGoods = goodsInfoMapper.getTotalGoods(pageQueryUtil);
        PageResult pageResult = new PageResult(totalGoods, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), goodsInfoList);

        return pageResult;
    }

    @Override
    public PageResult getCategoryGoodsPage(Integer goodsCategoryId, PageQueryUtil pageQueryUtil) {
        List<GoodsInfo> goodsInfoByCategory = goodsInfoMapper.findGoodsInfoByCategory(pageQueryUtil);
        int total = goodsInfoMapper.getTotalGoodsByCategory(goodsCategoryId, pageQueryUtil);
        PageResult pageResult = new PageResult(total, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), goodsInfoByCategory);

        return pageResult;
    }

    @Override
    public int getTotalGoods(PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.getTotalGoods(pageQueryUtil);
    }

    @Override
    public int getTotalGoodsByCategory(Integer goodsCategoryId, PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.getTotalGoodsByCategory(goodsCategoryId, pageQueryUtil);
    }

    @Override
    public int getTotalGoodsBySearch(PageQueryUtil pageQueryUtil) {
        return goodsInfoMapper.getTotalGoodsBySearch(pageQueryUtil);
    }

    @Override
    public PageResult searchGoods(PageQueryUtil pageQueryUtil) {
        List<GoodsInfo> goodsInfoBySearch = goodsInfoMapper.findGoodsInfoBySearch(pageQueryUtil);
        int total = goodsInfoMapper.getTotalGoodsBySearch(pageQueryUtil);
//        List<GoodsInfo> goodsInfoList = new ArrayList<>();
//        if (!goodsInfoBySearch.isEmpty()) {
//            goodsInfoList = BeanUtils.copy
//        }

        PageResult pageResult = new PageResult(total, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), goodsInfoBySearch);

        return pageResult;
    }
}
