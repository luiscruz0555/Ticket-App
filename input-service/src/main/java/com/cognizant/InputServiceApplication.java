package com.cognizant;

import com.cognizant.exceptions.handlers.ControllerExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import(ControllerExceptionHandler.class)
public class InputServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InputServiceApplication.class, args);
	}

}
