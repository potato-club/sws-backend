package com.sws.sws.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://localhost:3000",
                        "https://sws-back.shop",
                        "https://www.sws-back.shop")
                .exposedHeaders("authorization", "refreshToken", "Set-Cookie")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS","PATCH")
                .allowCredentials(true);
    }
}
