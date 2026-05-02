package com.barrioapp.api_padre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiPadreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPadreApplication.class, args);
	}

}
