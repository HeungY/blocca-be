package com.theo.quixx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class QuixxApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuixxApplication.class, args);
	}

}
