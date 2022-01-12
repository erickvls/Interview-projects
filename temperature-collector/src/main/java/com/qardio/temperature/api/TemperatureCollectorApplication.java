package com.qardio.temperature.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TemperatureCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureCollectorApplication.class, args);
	}

}
