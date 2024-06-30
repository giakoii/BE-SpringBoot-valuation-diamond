package org.swp391.valuationdiamond.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
//public class WebConfig {
//
//
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter());
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng CORS cho tất cả các URL
                .allowedOrigins("http://localhost:5173")
//                .allowedOrigins("http://localhost:5174")// Chỉ định nguồn được phép
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}