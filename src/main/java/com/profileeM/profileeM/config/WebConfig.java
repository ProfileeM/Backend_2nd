package com.profileeM.profileeM.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { // CORS 설정

    private final JasyptConfig jasyptConfig;

    public WebConfig(JasyptConfig jasyptConfig) {
        this.jasyptConfig = jasyptConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
//                        "http://192.168.45.142:8082",
                        "https://profileem.netlify.app",
//                        "http://localhost:3000",
                        jasyptConfig.stringEncryptor().decrypt("g9l9W60Ebz9BFvCr2yGblFiahiuOBzmbdqZEv8ZxKUSiwK8ZyPBXZQ==")
                )
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
