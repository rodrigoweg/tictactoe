package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.board.ai.AIMove;
import com.metronom.tictactoe.exceptions.BoarStatusException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.utils.Const;

public class Computer extends Player {

    public Computer(String name, Character character) {
        super(name, character);
    }

    public Move move(Board board) throws InvalidFormatMovementException {
        try {
            board.paint(Const.PROD);
            return AIMove.getNextCleverMovement(board,this);
        } catch (BoarStatusException e) {
            e.printStackTrace();
            return null;
        }
    }
}
