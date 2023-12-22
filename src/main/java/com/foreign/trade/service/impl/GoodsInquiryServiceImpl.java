package com.foreign.trade.service.impl;

import com.foreign.trade.dao.GoodsInquiryMapper;
import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.service.GoodsInquiryService;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInquiryServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:49:00
 **/
@Service
public class GoodsInquiryServiceImpl implements GoodsInquiryService {

    @Resource
    private GoodsInquiryMapper goodsInquiryMapper;

    @Override
    public int inquiry(GoodsInquiry goodsInquiry) {
        return goodsInquiryMapper.inquiry(goodsInquiry);
    }

    @Override
    public List<GoodsInquiry> findInquiryHistory(PageQueryUtil pageQueryUtil) {
        return goodsInquiryMapper.findInquiryHistory(pageQueryUtil);
    }

    @Override
    public PageResult getInquiryHistoryPage(PageQueryUtil pageQueryUtil) {
        List<GoodsInquiry> inquiryHistory = goodsInquiryMapper.findInquiryHistory(pageQueryUtil);
        int total = goodsInquiryMapper.getTotalInquiry(pageQueryUtil);
        PageResult pageResult = new PageResult(total, pageQueryUtil.getLimit(), pageQueryUtil.getCurrPage(), inquiryHistory);

        return pageResult;
    }

    @Override
    public GoodsInquiry getInquiryById(Integer inquiryId) {
        return goodsInquiryMapper.getInquiryById(inquiryId);
    }

    @Override
    public int deleteInquiry(Integer[] inquiryIds) {
        return goodsInquiryMapper.deleteInquiry(inquiryIds);
    }

    @Override
    public int getTotalInquiry(PageQueryUtil pageQueryUtil) {
        return goodsInquiryMapper.getTotalInquiry(pageQueryUtil);
    }
}
