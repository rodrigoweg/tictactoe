package com.metronom.tictactoe.utils;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.controller.TictactoeController;
import com.metronom.tictactoe.controller.TictactoeControllerImp;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.service.TicTacToeService;
import com.metronom.tictactoe.service.TictactoeServiceImp;

public class SingletonObjectFactory {
    ConfigLoader configLoader = null;

    public ConfigLoader getConfigLoader(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public TictactoeController getTictactoeController() throws LoadPropertiesException {
        return new TictactoeControllerImp(this);
    }

    public Board getBoard(int size){
        return new Board(size);
    }

    public TicTacToeService getTictactoeService() throws LoadPropertiesException {
        return new TictactoeServiceImp(this);
    }
}
