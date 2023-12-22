package com.foreign.trade.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: LoginSuccessHandler.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 12:54:00
 **/
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Strict-Origin-When-Cross-Origin", "true");
        response.setContentType("application/json;charset=UTF-8");
        if (authentication.isAuthenticated()) {
            logger.info("授权成功");
//            redirectStrategy.sendRedirect(request, response, "/admin/index");
            response.sendRedirect("/admin/index");
        } else {
            logger.info("授权失败");
        }
    }
}
