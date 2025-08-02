package com.example.travelproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 보안 스키마 정의
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // 모든 엔드포인트에 보안 적용
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // API 기본 정보
                .info(new Info()
                        .title("TravelProject Authentication API")
                        .version("1.0")
                        .description("회원가입, 로그인, 토큰갱신, 로그아웃 기능")
                );
    }
}