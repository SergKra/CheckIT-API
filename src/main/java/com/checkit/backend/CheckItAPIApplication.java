package com.checkit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.checkit.backend.idealogic.controllers.IdeaController;

@SpringBootApplication
public class CheckItAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
