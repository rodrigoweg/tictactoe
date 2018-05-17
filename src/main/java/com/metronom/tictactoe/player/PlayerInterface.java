package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;

/**
 * This interface should be implemented by each players that wants to play
 */
public interface PlayerInterface {
    //The player may need the information about the board
    Move move(Board board) throws InvalidFormatMovementException, TictactoeExcepiton;
    Character getCharacter();
    String getName();
}
