package com.metronom.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(TictactoeApplication.class, args);
	}
}
