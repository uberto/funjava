package com.ubertob.funjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunJavaApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository) {
		return (args) -> {
			// Create and save initial users
			userRepository.save(new AppUser( "Alice", "alice@example.com"));
			userRepository.save(new AppUser( "Bob", "bob@example.com"));
			userRepository.save(new AppUser( "Charlie", "charlie@example.com"));
		};
	}

}
