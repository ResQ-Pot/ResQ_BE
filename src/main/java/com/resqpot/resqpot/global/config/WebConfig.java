package com.resqpot.resqpot.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 우리 서버의 모든 API 경로에 대해
                .allowedOriginPatterns("*") // 일단 모든 출처(도메인) 허용 (테스트용)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키나 인증 헤더(JWT 토큰 등)를 포함할 수 있게 허용
                .maxAge(3600); // 브라우저가 이 허락을 1시간(3600초) 동안 기억하게 함 (성능 최적화)
    }
}