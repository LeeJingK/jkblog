package com.ljk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * description: UploadFilePathConfig
 * author: JKL
 * date: 2021/4/19 16:29
 */
@Configuration
public class UploadFilePathConfig  implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/image/**").addResourceLocations("classpath:/static/image/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:/home/images/");

        //windows本地文件目录
//        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/images/");
    }
}
