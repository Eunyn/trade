package com.foreign.trade.controller.common;

import com.foreign.trade.util.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: ExceptionHandlerController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 18:36:00
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        Result result = new Result();
        result.setCode(500);
        if (!(e instanceof GlobalException)) {
            e.printStackTrace();
            result.setMessage("未知异常");
        } else {
            result.setMessage(e.getMessage());
        }

        // 判断请求是否为Ajax，如果是，则返回Result json串，否则，则返回error试图
        String contentTypeHeader = request.getHeader("Content-Type");
        String acceptHeader = request.getHeader("Accept");
        String xRequest = request.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || ("XMLHttpRequest".equalsIgnoreCase(xRequest))) {
            return result;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            return modelAndView;
        }
    }

    @ExceptionHandler({RedisConnectionFailureException.class, QueryTimeoutException.class})
    public Object handleRedisConnectionFailure(HttpServletRequest request, DataAccessException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("stackTrace", e.getStackTrace());
        return modelAndView;
    }
}
