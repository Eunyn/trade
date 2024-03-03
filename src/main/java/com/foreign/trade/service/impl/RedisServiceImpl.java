package com.foreign.trade.service.impl;

import com.foreign.trade.config.Constants;
import com.foreign.trade.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: RedisServiceImpl.java
 * @Description: TODO
 * @CreateTime: 2023/12/13 14:04:00
 **/
@Service
public class RedisServiceImpl implements RedisService {

    private static final String ACCESS_COUNT_KEY = "website_access_count";

    private static final String PRODUCT_VISIT_KEY = "product_visits";

    private static final String PRODUCT_INQUIRY_KEY = "product_inquiry";

    private static final String UNKNOWN_IP_ATTEMPT_LOGIN = "unknown_ip_attempt_login:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Long incrementAccessCount() {
        return redisTemplate.opsForValue().increment(ACCESS_COUNT_KEY);
    }

    @Override
    public Long getAccessCount() {
        String accessCount = redisTemplate.opsForValue().get(ACCESS_COUNT_KEY);

        return accessCount != null ? Long.parseLong(accessCount) : 0;
    }

    @Override
    public void incrementProductVisitCount(String productName) {
        redisTemplate.opsForZSet().incrementScore(PRODUCT_VISIT_KEY, String.valueOf(productName), 1);
    }

    @Override
    public Map<Object, Double> getTopProductVisits(int count) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(PRODUCT_VISIT_KEY, 0, count - 1);
        Map<Object, Double> result = new LinkedHashMap<>();
        assert typedTuples != null;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            result.put(tuple.getValue(), tuple.getScore());
        }

        return result;
    }

    @Override
    public void incrementProductInquiryCount(String productName) {
        redisTemplate.opsForZSet().incrementScore(PRODUCT_INQUIRY_KEY, productName, 1);
    }

    @Override
    public Map<Object, Double> getTopProductInquiries(int count) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(PRODUCT_INQUIRY_KEY, 0, count - 1);
        Map<Object, Double> result = new LinkedHashMap<>();
        assert typedTuples != null;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            result.put(tuple.getValue(), tuple.getScore());
        }

        return result;
    }

    @Override
//    @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyCounters() {
        String currentDate = LocalDate.now().toString();
        String key = Constants.ACCESS_DAILY + currentDate;
        redisTemplate.opsForValue().set(key, "0");
    }

    @Override
    public String getAccessCounts(String key) {
        return redisTemplate.opsForValue().get(key) != null ? redisTemplate.opsForValue().get(key) : "0";
    }

    @Override
    public void deleteKeyByProduct(String productName) {
        redisTemplate.opsForZSet().remove(PRODUCT_INQUIRY_KEY, productName);
        redisTemplate.opsForZSet().remove(PRODUCT_VISIT_KEY, productName);
    }

    @Override
    public void addUnknownIP(String ip) {
        redisTemplate.opsForValue().set(UNKNOWN_IP_ATTEMPT_LOGIN + ip, "unknown ip");
        // 当未知 IP 尝试登录失败后存入 redis，15天内不能再访问登录链接
        redisTemplate.expire(UNKNOWN_IP_ATTEMPT_LOGIN + ip, 15, TimeUnit.DAYS);
    }

    @Override
    public boolean isExistUnknownIP(String ip) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(UNKNOWN_IP_ATTEMPT_LOGIN + ip));
    }


}
