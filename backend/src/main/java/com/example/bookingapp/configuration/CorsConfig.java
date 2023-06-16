package com.example.bookingapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Adjust the mapping pattern to match your API endpoints
                .allowedOrigins("http://localhost:3000") // Allow requests from your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow the HTTP methods you need
                .allowedHeaders("*"); // Allow all headers
    }
}
