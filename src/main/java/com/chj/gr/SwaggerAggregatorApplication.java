package com.chj.gr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SwaggerAggregatorApplication {
	
    public static void main(String[] args) {
    	
        SpringApplication.run(SwaggerAggregatorApplication.class, args);
    }
}
