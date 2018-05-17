package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.player.Computer;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.ConfigLoader;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.SingletonObjectFactory;
import com.metronom.tictactoe.utils.enums.GameOption;
import com.metronom.tictactoe.utils.enums.StatusGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TictactoeControllerImpTest {

    Properties tttProperties = null;
    ConfigLoader configLoader = null;
    SingletonObjectFactory sof = null;
    Board board = null;
    Computer computer = null;
    Move move = null;

    @Before
    public void setUp() throws LoadPropertiesException, InvalidFormatMovementException {
        tttProperties = mock(Properties.class);
        configLoader = mock(ConfigLoader.class);
        when(configLoader.loadProperties()).thenReturn(tttProperties);
        when(tttProperties.getProperty(Const.PROPERTY_CHARACTER_COMPUTER)).thenReturn("x");
        when(tttProperties.getProperty(Const.PROPERTY_CHARACTER_PLAYER1)).thenReturn("y");
        when(tttProperties.getProperty(Const.PROPERTY_CHARACTER_PLAYER2)).thenReturn("z");
        when(tttProperties.getProperty(Const.PROPERTY_GAME_OPTION)).thenReturn(GameOption.USERSVSCOMPUTER.toString());
        when(tttProperties.getProperty(Const.PROPERTY_PLAYGROUND_SIZE)).thenReturn(3+"");
        when(tttProperties.getProperty(Const.PROPERTY_LOG_LEVEL)).thenReturn(Const.PROD);
        computer = mock(Computer.class);
        when(computer.getName()).thenReturn("computer");
        board = mock(Board.class);
        when(board.getActivePlayer()).thenReturn(computer);
        when(board.evaluateBoard()).thenReturn(StatusGame.FINISH);
        move = mock(Move.class);
        when(computer.move(board)).thenReturn(move);
        sof = mock(SingletonObjectFactory.class);
        when(sof.getBoard(Mockito.anyInt())).thenReturn(board);
        when(sof.getConfigLoader()).thenReturn(configLoader);
    }

    @Test
    public void doActionInit() throws LoadPropertiesException, ControllerNotReadyException {

        TictactoeController controller = new TictactoeControllerImp(sof);
        StatusGame status = controller.doAction(StatusGame.INIT);
        assertEquals(status,StatusGame.NEXT_MOVE);
    }

    @Test
    public void doActionNextMove() throws LoadPropertiesException, ControllerNotReadyException {
        TictactoeController controller = new TictactoeControllerImp(sof);
        StatusGame status = controller.doAction(StatusGame.NEXT_MOVE);
        assertEquals(status,StatusGame.EVALUATE_BOARD);
    }

    @Test
    public void doActionEvaluate() throws LoadPropertiesException, ControllerNotReadyException {
        TictactoeController controller = new TictactoeControllerImp(sof);
        StatusGame status = controller.doAction(StatusGame.EVALUATE_BOARD);
        assertEquals(status,StatusGame.FINISH);
    }

    @Test
    public void doActionFinsishDraw() throws LoadPropertiesException, ControllerNotReadyException {
        TictactoeController controller = new TictactoeControllerImp(sof);

        StatusGame status = controller.doAction(StatusGame.FINISH);
        assertEquals(status,StatusGame.STOP);
    }

    @Test
    public void doActionFinishWin() throws LoadPropertiesException, ControllerNotReadyException {
        TictactoeController controller = new TictactoeControllerImp(sof);
        when(board.getWinner()).thenReturn(computer);
        StatusGame status = controller.doAction(StatusGame.FINISH);
        assertEquals(status,StatusGame.STOP);
    }



}
