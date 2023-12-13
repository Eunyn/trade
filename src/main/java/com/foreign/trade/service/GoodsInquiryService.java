package com.foreign.trade.service;

import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.util.PageQueryUtil;
import com.foreign.trade.util.PageResult;

import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsInquiryService.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:37:00
 **/
public interface GoodsInquiryService {
    int inquiry(GoodsInquiry goodsInquiry);

    List<GoodsInquiry> findInquiryHistory(PageQueryUtil pageQueryUtil);

    PageResult getInquiryHistoryPage(PageQueryUtil pageQueryUtil);

    GoodsInquiry getInquiryById(Integer inquiryId);

    int deleteInquiry(Integer[] inquiryIds);

    int getTotalInquiry(PageQueryUtil pageQueryUtil);
}
