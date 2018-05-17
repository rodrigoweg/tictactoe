package com.metronom.tictactoe.service;

import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;

/**
 * This interface should be implemented to play a game
 */
public interface TicTacToeService {
    void start() throws BoardStatusException, ControllerNotReadyException, TictactoeExcepiton;
    void stop();
}
