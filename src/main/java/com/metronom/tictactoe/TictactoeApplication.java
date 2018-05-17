package com.metronom.tictactoe;

import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.service.TicTacToeService;
import com.metronom.tictactoe.service.TictactoeServiceImp;
import com.metronom.tictactoe.utils.ConsoleUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
		try {
			TicTacToeService ticTacToeService = new TictactoeServiceImp();
			ConsoleUtility.logProd("Let's play Tic Tac Toe!");
			ticTacToeService.start();
		} catch (LoadPropertiesException e) {
			ConsoleUtility.error(e.getMessage());
			e.printStackTrace();
		} catch (BoardStatusException e) {
			ConsoleUtility.error(e.getMessage());
			e.printStackTrace();
		} catch (ControllerNotReadyException e) {
			ConsoleUtility.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
