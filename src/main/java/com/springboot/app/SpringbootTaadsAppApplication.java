package com.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@ComponentScan("com.springboot")
public class SpringbootTaadsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTaadsAppApplication.class, args);
		System.out.println("Server Started");
	}

}
