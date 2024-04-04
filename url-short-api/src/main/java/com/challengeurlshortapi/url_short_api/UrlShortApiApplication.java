package com.challengeurlshortapi.url_short_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UrlShortApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortApiApplication.class, args);
	}

}
