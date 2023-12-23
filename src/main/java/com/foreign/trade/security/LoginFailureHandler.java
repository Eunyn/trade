package com.foreign.trade.security;

import com.foreign.trade.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    final private Map<String, Integer> unknownIP = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.getSession().setAttribute("errorMsg", "Wrong username or password");

        String ipAddress = request.getRemoteAddr();
        int count = unknownIP.getOrDefault(ipAddress, 0) + 1;
        unknownIP.put(ipAddress, count);

        logger.info("User login fail. Login IP: {}", ipAddress);

        if (count >= 3) {
            logger.info("Too many failed logins: {}", count);
            boolean existUnknownIP = redisService.isExistUnknownIP(ipAddress);
            if (!existUnknownIP) {
                redisService.addUnknownIP(ipAddress);
            }
            // throw new GlobalException("Too many failed logins");
            response.sendRedirect("/error/error_404");

            return;
        }

        response.sendRedirect("/admin/login");
    }
}
