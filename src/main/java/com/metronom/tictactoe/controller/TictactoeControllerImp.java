package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.ConfigLoader;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.GameOption;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.Properties;

public class TictactoeControllerImp implements  TictactoeController{

    private GameOption gameOption;
    private Board board;
    private Properties tttProperties;

    public TictactoeControllerImp() throws LoadPropertiesException {
        initTicTacToe();
    }

    public StatusGame doAction(StatusGame statusGame) throws ControllerNotReadyException {
        if(!isControllerInitiated()){
            throw new ControllerNotReadyException();
        }
        switch (statusGame) {
            case INIT:
                statusGame = StatusGame.PAINT_BOARD_WITH_COORDINATES;
                ConsoleUtility.logProd(Const.GAME_START);
                break;
            case ERROR_LOAD_PROPERTIES:
                ConsoleUtility.error(Const.STOP_GAME);
                statusGame = StatusGame.STOP;
                break;
            case PAINT_BOARD_WITH_COORDINATES:
                ConsoleUtility.logProd(Const.FIND_BELOW_COORDINATES);
                board.paintCoordinates();
                ConsoleUtility.logProd(Const.LETS_PLAY);
                statusGame = StatusGame.NEXT_MOVE;
                break;
            case NEXT_PLAYER:
                board.updateActivePlayerToNextPlayer();
                ConsoleUtility.logDebug("Next player: " + board.getActivePlayer().getName());
                statusGame = StatusGame.NEXT_MOVE;
                break;
            case NEXT_MOVE:
                try {
                    PlayerInterface player = board.getActivePlayer();
                    Move move = player.move(board);
                    ConsoleUtility.logProd(player.getName() + "("+player.getCharacter()+") moved X: " + move.getX() + " and Y: " + move.getY());
                    board.doMovement(move);
                    statusGame = StatusGame.EVALUATE_BOARD;
                } catch (InvalidFormatMovementException e) {
                    ConsoleUtility.error(e.getMessage());
                    statusGame = StatusGame.NEXT_MOVE;
                }
                break;
            case EVALUATE_BOARD:
                statusGame = board.evaluateBoard();
                break;
            case FINISH:
                if (board.getWinner() != null) {
                    ConsoleUtility.logProd(Const.CONGRATULATIONS + board.getWinner().getName() + Const.WINNER_MESSAGE);
                } else {
                    ConsoleUtility.logProd(Const.GAME_COMPLETED);
                }
                board.paint(Const.PROD);
                statusGame = StatusGame.STOP;
                break;
        }
        return statusGame;
    }

    private boolean isControllerInitiated() {
        return board != null && gameOption != null && tttProperties != null;
    }

    private void initTicTacToe() throws LoadPropertiesException {
        tttProperties = ConfigLoader.loadProperties();
        if(tttProperties!=null){
            String human1Character = tttProperties.getProperty(Const.PROPERTY_CHARACTER_PLAYER1);
            String human2Character = tttProperties.getProperty(Const.PROPERTY_CHARACTER_PLAYER2);
            String computerCharacter = tttProperties.getProperty(Const.PROPERTY_CHARACTER_COMPUTER);
            int boardSize = Integer.parseInt(tttProperties.getProperty(Const.PROPERTY_PLAYGROUND_SIZE));

            gameOption = GameOption.valueOf(tttProperties.getProperty(Const.PROPERTY_GAME_OPTION));
            board = new Board(boardSize);
            board.initPlayers(gameOption, human1Character, human2Character, computerCharacter);
        }
    }

}
