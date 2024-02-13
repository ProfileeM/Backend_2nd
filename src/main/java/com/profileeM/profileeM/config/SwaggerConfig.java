package com.profileeM.profileeM.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "profileeM API 명세서",
                description = "profileeM 서버 API 명세서"))
@Configuration
public class SwaggerConfig {
}
