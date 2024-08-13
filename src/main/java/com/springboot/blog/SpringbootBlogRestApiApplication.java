package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest API's",
				description = "Spring Boot Blog App REST API's Documentation", 
				version = "v1.0",
				contact = @Contact(
						name = "Swapnil",
						email =  "swapnilpathak1999@gmail.com",
						url = ""
				),
				license = @License(
						name = "Apache 2.0",
						url = ""
						)
				
				),
				externalDocs = @ExternalDocumentation(
						description = "Spring Boot Blog App Documentation",
						url = ""
						) 
		)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper moddelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
