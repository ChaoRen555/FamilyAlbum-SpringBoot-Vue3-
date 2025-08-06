package com.album.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allowed cross-origin paths
                .allowedOriginPatterns("*") // Recommended in Spring Boot 2.4+ (supports wildcards)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed request headers
                .allowCredentials(true) // Whether to allow sending cookies
                .maxAge(3600); // Cache duration for preflight requests (in seconds)
    }

}
