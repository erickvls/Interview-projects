package com.bol.mancala.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class MancalaApplication {


	public static void main(String[] args) {
		Map<String, List<String>> people = new HashMap<>();
		people.put("John", Arrays.asList("555-1123", "555-3389"));
		people.put("Mary", Arrays.asList("555-2243", "555-5264"));
		people.put("Steve", Arrays.asList("555-6654", "555-3242"));

		List<String> phones = people.values().stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());


		SpringApplication.run(MancalaApplication.class, args);
		System.err.println(phones);
	}

}
