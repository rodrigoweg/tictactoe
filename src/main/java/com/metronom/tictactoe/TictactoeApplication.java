package com.metronom.tictactoe;

import com.metronom.tictactoe.utils.SingletonObjectFactory;
import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.service.TicTacToeService;
import com.metronom.tictactoe.service.TictactoeServiceImp;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TictactoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
		try {
			String play = Const.YES;
			ConsoleUtility.logProd(Const.LETS_PLAY);
			while (play.toLowerCase().startsWith(Const.YES)){
				SingletonObjectFactory sof = new SingletonObjectFactory();
				TicTacToeService ticTacToeService = new TictactoeServiceImp(sof);
				ticTacToeService.start();
				ConsoleUtility.logProd(Const.PLAY_AGAIN);
				Scanner scanner = new Scanner(System.in);
				play = scanner.next();
			}

			ConsoleUtility.logProd(Const.THANKS);
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
		finally {
			System.exit(0);
		}
	}
}
