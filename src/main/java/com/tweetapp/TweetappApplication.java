package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan(basePackages = {"com.tweetapp.service",
		"com.tweetapp.security",
		"com.tweetapp.config",
		"com.tweetapp.model",
		"com.tweetapp.exception",
		"com.tweetapp.api"})
@SpringBootApplication
@EnableMongoRepositories("com.tweetapp.repository")
@EnableSwagger2
public class TweetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetappApplication.class, args);
	}

}
