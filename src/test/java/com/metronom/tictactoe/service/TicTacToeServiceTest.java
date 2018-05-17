package com.metronom.tictactoe.service;

import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.utils.SingletonObjectFactory;
import com.metronom.tictactoe.controller.TictactoeController;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.ControllerNotReadyException;
import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.utils.enums.StatusGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TicTacToeServiceTest {

    @Test
    public void startTest() throws LoadPropertiesException, ControllerNotReadyException, BoardStatusException, TictactoeExcepiton {

        TictactoeController tictactoeController = mock(TictactoeController.class);
        when(tictactoeController.doAction(StatusGame.INIT)).thenReturn(StatusGame.NEXT_MOVE);
        when(tictactoeController.doAction(StatusGame.NEXT_MOVE)).thenReturn(StatusGame.EVALUATE_BOARD);
        when(tictactoeController.doAction(StatusGame.EVALUATE_BOARD)).thenReturn(StatusGame.FINISH);
        when(tictactoeController.doAction(StatusGame.FINISH)).thenReturn(StatusGame.STOP);
        SingletonObjectFactory sof = mock(SingletonObjectFactory.class);
        when(sof.getTictactoeController()).thenReturn(tictactoeController);
        TicTacToeService service = new TictactoeServiceImp(sof);
        service.start();
    }
}
