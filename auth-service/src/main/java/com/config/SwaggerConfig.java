package com.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI authOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Auth Service API")
                .description("Authentication service (JWT) for Microservices E-Commerce Platform")
                .version("v1.0"));
    }
}
