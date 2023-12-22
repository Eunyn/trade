package com.foreign.trade.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String servletPath = request.getServletPath();
//        if (servletPath.startsWith("/admin") && null == request.getSession().getAttribute("loginUserId")) {
//            request.getSession().setAttribute("errorMsg", "Please log in");
//            response.sendRedirect(request.getContextPath() + "/admin/login");
//            return false;
//        } else {
//            request.getSession().removeAttribute("errorMsg");
//            return true;
//        }
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
