package com.foreign.trade.config;

import com.foreign.trade.security.LoginFailureHandler;
import com.foreign.trade.security.LoginSuccessHandler;
import com.foreign.trade.security.UserLogoutSuccessHandler;
import com.foreign.trade.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: WebSecurityConfig.java
 * @Description: TODO
 * @CreateTime: 2023/12/21 11:37:00
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Resource
    private LoginSuccessHandler successHandler;

    @Resource
    private LoginFailureHandler failureHandler;

    public static final String[] whitelist = {"/", "/admin/login", "/common/**", "/error/**", "/mall/**", "/upload/**"};

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().sameOrigin().and()
//                .authorizeRequests()
//                .antMatchers(whitelist).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/admin/login")
//                .loginProcessingUrl("/admin/login")
//                .usernameParameter("userName")
//                .passwordParameter("userPassword")
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and()
//                .logout()
//                .logoutUrl("/admin/logout")
//                .logoutSuccessHandler(new UserLogoutSuccessHandler())
//                .and()
//                .csrf()
//                .disable()
//                .cors();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/admin/css/**", "/admin/js/**", "/admin/fonts/**", "/mall/**", "/common/**");
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers(whitelist).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("userName")
                .passwordParameter("userPassword")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessHandler(new UserLogoutSuccessHandler())
                .and()
                .csrf()
                .disable()
                .cors();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/admin/css/**", "/admin/js/**", "/admin/fonts/**", "/admin/img/**");
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
