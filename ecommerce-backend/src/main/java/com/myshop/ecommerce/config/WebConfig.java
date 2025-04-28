package com.myshop.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Allow CORS only for API paths
                .allowedOrigins("http://127.0.0.1:5500") // Your frontend URL (adjust if different port)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Methods you want to allow
                .allowedHeaders("*"); // Allow all headers
    }
}
