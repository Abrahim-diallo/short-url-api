package com.challengeurlshortapi.url_short_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapperBean() {
        return new JsonMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }


}