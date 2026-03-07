package com.resqpot.resqpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ResqpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResqpotApplication.class, args);
	}

}
