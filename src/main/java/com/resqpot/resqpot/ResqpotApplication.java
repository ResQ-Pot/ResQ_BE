package com.resqpot.resqpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling  // 스케줄러 작동 스위치
@SpringBootApplication
public class ResqpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResqpotApplication.class, args);
	}

}
