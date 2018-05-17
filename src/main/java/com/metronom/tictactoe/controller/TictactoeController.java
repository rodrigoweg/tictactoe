package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.utils.enums.StatusGame;

public interface TictactoeController {
    StatusGame doAction(StatusGame nextState) throws ControllerNotReadyException;
}
