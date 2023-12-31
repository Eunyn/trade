package com.foreign.trade.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: ErrorController.java
 * @Description: TODO
 * @CreateTime: 2023/12/1 18:29:00
 **/
@Controller
public class ErrorController implements ErrorViewResolver {

    private static ErrorController errorController;

    @Resource
    ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public ErrorController() {
        if (errorController == null)
            errorController = new ErrorController(errorAttributes);
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        if (HttpStatus.NOT_FOUND == status) {
            return new ModelAndView("error/error_404");
        } else {
            return new ModelAndView("error/error_5xx");
        }
    }

    @RequestMapping("/error/error_404")
    public String handleErrorRequest(HttpServletRequest request) {

        return "/error/error_404";
    }

}
