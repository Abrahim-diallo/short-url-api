package com.challengeurlshortapi.url_short_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Configures the custom OpenAPI for Swagger documentation.
     *
     * @return OpenAPI object containing the configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("URL API").description("This api provides APIs for managing short url api.").version("v1.0.0"));
    }
}