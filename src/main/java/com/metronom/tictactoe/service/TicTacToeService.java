package com.metronom.tictactoe.service;

import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.BoardStatusException;

public interface TicTacToeService {
    void start() throws BoardStatusException, ControllerNotReadyException;
    void stop();
}
