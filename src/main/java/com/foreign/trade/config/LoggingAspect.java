package com.foreign.trade.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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

    private Long startTime;

    @Pointcut("execution(* com.foreign.trade.controller..*.*(..))")
    public void executionPointcut() {

    }

    @Before("executionPointcut()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        startTime = System.currentTimeMillis();
        logger.info("Request URL: {}", request.getRequestURL().toString());
        logger.info("Request IP: {}", request.getRemoteAddr());
        logger.info("Request Parameter: {}", joinPoint.getArgs());
        logger.info("Executing method: {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "executionPointcut()", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        Long endTime = System.currentTimeMillis();
        logger.info("Time token to execute: {}", endTime - startTime);
        logger.info("Returned value: {}", result);
    }
}

