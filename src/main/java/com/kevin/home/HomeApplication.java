package com.kevin.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kevin.home.controllers","com.kevin.home.config"})
@EnableJpaRepositories(basePackages = "com.kevin.home.domain")
public class HomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeApplication.class, args);
	}

}
