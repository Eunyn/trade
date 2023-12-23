package com.foreign.trade.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: LogoutSuccessHandler.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 13:32:00
 **/
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    final private static Logger logger = LoggerFactory.getLogger(UserLogoutSuccessHandler.class);
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("User logout success.");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("/admin/login");
    }
}
