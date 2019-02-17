package com.its.springtipsreactivetestingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringTipsReactiveTestingConsumerApplication {

	@Bean
	WebClient webclient(WebClient.Builder builder) {
		return builder.build();
	};

	public static void main(String[] args) {
		SpringApplication.run(SpringTipsReactiveTestingConsumerApplication.class, args);
	}

}

