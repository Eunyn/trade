package com.foreign.trade.config;

import com.foreign.trade.security.LoginFailureHandler;
import com.foreign.trade.security.LoginSuccessHandler;
import com.foreign.trade.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: WebSecurityConfig.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 11:37:00
 **/
@Component
@EnableWebSecurity
public class WebSecurityConfig {

    @Resource
    private LoginSuccessHandler successHandler;

    @Resource
    private LoginFailureHandler failureHandler;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/admin/login","/common/**",
                                "/mall/**", "/error/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin()
                .loginPage("/admin/login")
//                .loginProcessingUrl("/admin/login")
                .usernameParameter("userName")
                .passwordParameter("userPassword")
                .successHandler(successHandler)
                .failureHandler(failureHandler);
//                .failureUrl("/admin/login");
//                .defaultSuccessUrl("/admin/index")
//                .successForwardUrl("/admin/index").and().logout().logoutUrl("/mall/index");

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return new CustomUserDetailsService();
    }
}
