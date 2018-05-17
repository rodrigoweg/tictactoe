package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.Scanner;

/**
 * This class represent the human player
 */
public class Human extends Player {
    public Human(String name, Character character) {
        super(name, character);
    }

    @Override
    /**
     * The player inserts a move via commands of console
     */
    public Move move(Board board) {
        boolean validMove = false;
        while (!validMove) {
            try {
                board.paint(Const.PROD);
                ConsoleUtility.logProd(getName() + "(" + getCharacter() + ")" + Const.ENTER_NEXT_MOVE);
                // Scanner to read command-line input
                ConsoleUtility.logProd(Const.ENTER_X_Y);
                Scanner scanner = new Scanner(System.in);
                String xy = scanner.next();
                Move move = validateMove(xy,board.getSize());
                validMove = true;
                return move;
            } catch (InvalidFormatMovementException e) {
                ConsoleUtility.error(e.getMessage());
            }
        }
        return null;
    }


}
