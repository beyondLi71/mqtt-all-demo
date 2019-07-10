package com.beyondli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
public class MqttAllDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttAllDemoApplication.class, args);
	}

}
