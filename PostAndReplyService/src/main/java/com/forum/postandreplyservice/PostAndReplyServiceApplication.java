package com.forum.postandreplyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PostAndReplyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostAndReplyServiceApplication.class, args);
	}

}
