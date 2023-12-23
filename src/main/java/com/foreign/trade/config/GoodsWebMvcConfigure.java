package com.foreign.trade.config;

import com.foreign.trade.interceptor.GoodsAdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: Eun
 * @Version 1.0.0
 * @ClassName: GoodsWebMvcConfigure.java
 * @Description: TODO
 * @CreateTime: 2023/12/3 19:09:00
 **/
@Configuration
public class GoodsWebMvcConfigure implements WebMvcConfigurer {

    @Resource
    private GoodsAdminInterceptor goodsAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(goodsAdminInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/mall/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/css/*")
                .excludePathPatterns("/admin/fonts/*")
                .excludePathPatterns("/admin/img/*")
                .excludePathPatterns("/admin/js/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
