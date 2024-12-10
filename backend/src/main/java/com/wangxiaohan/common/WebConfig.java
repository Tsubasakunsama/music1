package com.wangxiaohan.common;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.addPathPrefix("/WXH", clazz -> clazz.isAnnotationPresent(RestController.class));
    }
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/WXH/**")
                .excludePathPatterns("/WXH/admin/login")
                .excludePathPatterns("/WXH/admin/register")
                .excludePathPatterns("/WXH/admin/forget")
                .excludePathPatterns("/WXH/band")
                .excludePathPatterns("/WXH/song")
                .excludePathPatterns("/WXH/member")
                .excludePathPatterns("/WXH/concert")
                .excludePathPatterns("/WXH/album")
                .excludePathPatterns("/WXH/joins")
                .excludePathPatterns("/WXH/performs");
    }
}

