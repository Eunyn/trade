package com.foreign.trade.service;

import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: RedisService.java
 * @Description: TODO
 * @CreateTime: 2023/12/13 14:02:00
 **/
public interface RedisService {

    Long incrementAccessCount();

    Long getAccessCount();

    void incrementProductVisitCount(String productName);

    Map<Object, Double> getTopProductVisits(int count);

    void incrementProductInquiryCount(String productName);

    Map<Object, Double> getTopProductInquiries(int count);

    void resetDailyCounters();

    String getAccessCounts(String key);

    void deleteKeyByProduct(String productName);
}
