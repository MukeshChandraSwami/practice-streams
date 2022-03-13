package com.learn.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.learn")
public class LearnStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnStreamsApplication.class, args);
		testStartingApplication();
	}

	private static void testStartingApplication() {
		System.out.println(" ************  Application started  ************");
	}
}
