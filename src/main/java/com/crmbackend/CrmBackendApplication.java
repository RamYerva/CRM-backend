package com.crmbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication

public class CrmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmBackendApplication.class, args);
	}

}