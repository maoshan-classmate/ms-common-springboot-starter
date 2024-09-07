package com.ms.config;


import com.ms.handler.MsRequestBodyHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author maoshan-classmate
 * @date 2024/9/7 14:44
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new MsRequestBodyHandler());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }



}
