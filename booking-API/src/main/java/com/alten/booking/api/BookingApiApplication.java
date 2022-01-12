package com.alten.booking.api;

import com.alten.booking.api.domain.Room;
import com.alten.booking.api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Properties;

@SpringBootApplication
public class BookingApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(BookingApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner insertData(RoomRepository repo) {
		return args -> {
			Room room = Room.builder().id(1).price(new BigDecimal(120)).build();
			repo.save(room);
		};
	}

}
