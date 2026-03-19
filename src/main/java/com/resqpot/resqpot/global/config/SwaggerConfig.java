package com.resqpot.resqpot.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server; // 이거 임포트 추가!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI resqpotOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info()
                        .title("ResQpot API 명세서")
                        .description("재난 안전 서비스 ResQpot의 백엔드 API 문서입니다. 안드로이드 팀 파이팅! 🚀")
                        .version("v1.0.0"));
    }
}