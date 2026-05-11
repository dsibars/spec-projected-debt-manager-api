package com.dsibars.debtmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DebtManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebtManagerApplication.class, args);
	}

}
