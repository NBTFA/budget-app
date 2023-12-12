package com.me.budgetbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//跨域访问
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**") //允许跨域访问的路径
                .allowedOrigins("http://localhost:8080") //允许跨域访问的源
                .allowedMethods("POST", "GET", "PUT", "DELETE") //请求方法
                .maxAge(178000) //预检间隔时间
                .allowedHeaders("*") //请求头设置
                .allowCredentials(true); //是否发送cookie
    }
}
