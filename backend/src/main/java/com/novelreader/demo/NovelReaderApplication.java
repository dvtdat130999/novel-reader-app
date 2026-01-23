package com.novelreader.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NovelReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovelReaderApplication.class, args);
		System.out.println("Running.");
	}

}
