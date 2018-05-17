package com.metronom.tictactoe.service;

import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.utils.SingletonObjectFactory;
import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.controller.TictactoeController;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.StatusGame;

/**
 * This class will provide the service to play a game
 */
public class TictactoeServiceImp implements TicTacToeService{

    private boolean playActive;
    private TictactoeController tictactoeController;

    public TictactoeServiceImp(SingletonObjectFactory sof) throws LoadPropertiesException{
        this.tictactoeController = sof.getTictactoeController();
    }

    /**
     * This methods will start a game
     * @throws BoardStatusException
     * @throws ControllerNotReadyException
     * @throws TictactoeExcepiton
     */
    public void start() throws BoardStatusException, ControllerNotReadyException, TictactoeExcepiton {
        if(!playActive){
            playActive = true;
            StatusGame nextState = StatusGame.INIT;
            while (playActive && !nextState.equals(StatusGame.STOP)) {
                nextState = tictactoeController.doAction(nextState);
            }
            stop();
        }else{
            throw new BoardStatusException(Const.GAME_ALREADY_ACTIVE);
        }

    }

    public void stop(){
        playActive = false;
    }

}
