package com.foreign.trade.interceptor;

import com.foreign.trade.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsAdminInterceptor.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 12:40:00
 **/
@Component
public class GoodsAdminInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GoodsAdminInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();

        if (redisService.isExistUnknownIP(ipAddress)) {
            logger.info("The banned IP: {{}} tries to log in again.", ipAddress);

            response.sendRedirect("/error/error_404");

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
