// src/main/java/com/resqpot/resqpot/global/config/RestTemplateConfig.java
package com.resqpot.resqpot.global.config;

import com.fasterxml.jackson.databind.ObjectMapper; // 🌟 이거 추가!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 🌟 스프링 창고에 ObjectMapper 녀석도 추가!
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}