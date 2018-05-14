package com.metronom.tictactoe;

import com.metronom.tictactoe.service.TicTacToeService;
import com.metronom.tictactoe.utils.ConsoleUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
		TicTacToeService ticTacToeService = new TicTacToeService();
		ConsoleUtility.logProd("Let's play Tic Tac Toe!");
		ticTacToeService.start();
	}
}
