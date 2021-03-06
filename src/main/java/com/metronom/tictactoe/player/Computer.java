package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.board.ai.AIMove;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.utils.Const;

/**
 * This class represent the player Computer
 */
public class Computer extends Player {

    public Computer(String name, Character character) {
        super(name, character);
    }

    /**
     * This method will select a movment base on the AI
     * @param board Board with the status of the game
     * @return Next movement for the computer
     * @throws InvalidFormatMovementException
     * @throws TictactoeExcepiton
     */
    public Move move(Board board) throws InvalidFormatMovementException, TictactoeExcepiton {
        try {
            board.paint(Const.PROD);
            return AIMove.getNextCleverMovement(board,this);
        } catch (BoardStatusException e) {
            e.printStackTrace();
            return null;
        }
    }
}
