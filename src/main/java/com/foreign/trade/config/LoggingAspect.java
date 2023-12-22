package com.foreign.trade.config;

import com.foreign.trade.entity.GoodsInfo;
import com.foreign.trade.service.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: LoggingAspect.java
 * @Description: TODO
 * @CreateTime: 2023/12/12 9:23:00
 **/
@Component
@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("execution(* com.foreign.trade.controller..*.*(..))")
    public void executionPointcut() {

    }

    @Before("executionPointcut()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        HttpServletRequest request = getRequest();

        logger.info("Request URL: {}", request.getRequestURL().toString());
        logger.info("Request IP: {}", request.getRemoteAddr());
        logger.info("Request Parameter: {}", joinPoint.getArgs());
        logger.info("Executing method: {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "executionPointcut()", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = getRequest();
        GoodsInfo goodsInfo = (GoodsInfo) request.getAttribute("details");

        // 统计商品被访问次数
        if (goodsInfo != null && goodsInfo.getGoodsName() != null) {
            redisService.incrementProductVisitCount(goodsInfo.getGoodsName());
        }
        // 统计网页被访问次数，只统计首页
        Object indexPage = request.getAttribute("indexPage");
        if (indexPage != null) {
            redisService.incrementAccessCount();
            String currentDate = LocalDate.now().toString();
            redisTemplate.opsForValue().increment(Constants.ACCESS_DAILY + currentDate);
        }
        // 统计被 Inquiry 的商品
        String sendResult = (String) request.getAttribute("sendResult");
        if (sendResult != null) {
            redisService.incrementProductInquiryCount(sendResult);
        }

        logger.info("Returned value: {}", result);
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert attributes != null;
        return attributes.getRequest();
    }
}

