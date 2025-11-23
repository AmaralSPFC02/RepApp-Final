package com.republica.app;

import com.republica.app.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class RepAppBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(RepAppBackendApplication.class, args);
	}

}
