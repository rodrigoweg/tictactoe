package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.utils.enums.StatusGame;

/**
 * This class will manage the different states of the game
 */
public interface TictactoeController {
    StatusGame doAction(StatusGame nextState) throws ControllerNotReadyException, TictactoeExcepiton;
}
