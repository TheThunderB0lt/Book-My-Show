package com.driver.BookMyShow;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "MovieBooker Swagger UI", version = "1.0", description = "This Swagger page contains all the endpoints details of MovieBooker Application.",contact = @Contact(name = "The ThunderBolt", url = "https://thethunderb0lt.github.io/", email = "itsnikith@gmail.com")))
@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

}
