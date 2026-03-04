package com.ohss.ohss_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ohss")
@EnableJpaRepositories(basePackages = "com.ohss.repository")
@EntityScan(basePackages = "com.ohss.model")
public class OhssBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OhssBackendApplication.class, args);
	}

}
