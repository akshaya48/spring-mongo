package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.example.demo", "com.example.config", "com.example.util", "com.example.repo", "com.example.entity"})
@SpringBootApplication
public class SpringMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoApplication.class, args);
	}
	
	public HealthIndicator helloHealthIndicator() {
		return new HealthIndicator() {

			@Override
			public Health health() {
				return Health.up().withDetail("hello", "sports").build();
			}
		};
	}
}
