package com.purpletalk.hr.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HrApplicationJwtTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApplicationJwtTokenApplication.class, args);
	}

}
