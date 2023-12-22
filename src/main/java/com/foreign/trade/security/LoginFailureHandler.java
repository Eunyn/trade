package com.foreign.trade.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: LoginFailureHandler.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 12:55:00
 **/
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    final static private Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String remoteAddr = request.getRemoteAddr();
        logger.info("登录IP：{}", remoteAddr);
        logger.info("登录URL：{}", request.getRequestURL());

        response.sendRedirect("/admin/login");
    }
}
