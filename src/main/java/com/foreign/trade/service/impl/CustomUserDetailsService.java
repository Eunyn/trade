package com.foreign.trade.service.impl;

import com.foreign.trade.entity.GoodsAdmin;
import com.foreign.trade.service.GoodsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: CustomUserDetailsService.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 13:16:00
 **/
public class CustomUserDetailsService implements UserDetailsService {

    final private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Resource
    private GoodsAdminService goodsAdminService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("User--{{}}--attempts to login.", username);
        GoodsAdmin goodsAdmin = goodsAdminService.selectByName(username);
        if (goodsAdmin == null) {
//            logger.info("登录失败，没有此用户。");
            throw new UsernameNotFoundException("Login fail, no such user.");
        }

        return new User(goodsAdmin.getUserName(), passwordEncoder.encode(goodsAdmin.getUserPassword()), Collections.emptyList());
    }
}
