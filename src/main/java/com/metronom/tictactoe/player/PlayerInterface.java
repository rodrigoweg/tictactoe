package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;

public interface PlayerInterface {
    //The player may need the information about the board
    Move move(Board board) throws InvalidFormatMovementException;
    Character getCharacter();
    String getName();
}
