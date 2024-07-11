package com.ubertob.funjava;

import org.springframework.boot.SpringApplication;

public class TestFunjavaApplication {

	public static void main(String[] args) {
		SpringApplication.from(FunjavaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
