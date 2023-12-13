package com.foreign.trade.dao;

import com.foreign.trade.entity.GoodsInquiry;
import com.foreign.trade.util.PageQueryUtil;

import java.util.List;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsCartMapper.java
 * @Description: TODO
 * @CreateTime: 2023/11/28 21:33:00
 **/
public interface GoodsInquiryMapper {
    int inquiry(GoodsInquiry goodsInquiry);

    List<GoodsInquiry> findInquiryHistory(PageQueryUtil pageQueryUtil);

    GoodsInquiry getInquiryById(Integer inquiryId);

    int deleteInquiry(Integer[] inquiryId);

    int getTotalInquiry(PageQueryUtil pageQueryUtil);
}
