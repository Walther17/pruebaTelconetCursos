package com.demo.olimacservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class OlimacServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlimacServicesApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
		.info(new Info()
			.title("xddddd")
			.version("9.9")
			.description("null")
			.termsOfService("https://facebook.com")
			.license(new License().name("null").url("null")));
	}

}
