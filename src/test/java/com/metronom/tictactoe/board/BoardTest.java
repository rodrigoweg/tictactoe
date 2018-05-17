package com.metronom.tictactoe.board;

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
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BoardTest {

    Properties tttProperties = null;
    ConfigLoader configLoader = null;
    SingletonObjectFactory sof = null;
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
        when(computer.getCharacter()).thenReturn('x');
        sof = mock(SingletonObjectFactory.class);
        when(sof.getConfigLoader()).thenReturn(configLoader);
        move = mock(Move.class);
        when(move.getPlayer()).thenReturn(computer);
        when(move.getX()).thenReturn(1);
        when(move.getY()).thenReturn(1);

    }

    @Test
    public void initPlayersUserVSUserTest() {
        Board board = new Board(3);
        board.initPlayers(GameOption.USERVSUSER,"x","y","z");
        assertEquals(board.getPlayers().size(),2);
        assertNotEquals(board.getActivePlayer(),null);
        boolean equals = checkActiveUserIsRandomSelected(board);
        assertEquals(equals,false);
    }

    @Test
    public void initPlayersUserVSComputerTest() {
        Board board = new Board(3);
        board.initPlayers(GameOption.USERVSCOMPUTER,"x","y","z");
        assertEquals(board.getPlayers().size(),2);
        assertNotEquals(board.getActivePlayer(),null);
        boolean equals = checkActiveUserIsRandomSelected(board);
        assertEquals(equals,false);
    }
    @Test
    public void initPlayersUsersVSComputerTest() {
        Board board = new Board(3);
        board.initPlayers(GameOption.USERSVSCOMPUTER,"x","y","z");
        assertEquals(board.getPlayers().size(),3);
        assertNotEquals(board.getActivePlayer(),null);
        boolean equals = checkActiveUserIsRandomSelected(board);
        assertEquals(equals,false);
    }

    @Test
    public void paint() {
        Board board = new Board(3);
        board.paint(Const.PROD);
    }

    @Test
    public void paintCoordinates() {
        Board board = new Board(3);
        board.paintCoordinates();
    }

    @Test
    public void doMovement() throws InvalidFormatMovementException {
        Board board = new Board(3);
        board.doMovement(move);
    }

    private boolean checkActiveUserIsRandomSelected(Board board) {
        //check active user is random selected
        PlayerInterface player1 = board.getActivePlayer();
        boolean equals = true;
        for(int i = 0; i < 100; i++){
            board.initPlayers(GameOption.USERVSUSER,"x","y","z");
            PlayerInterface player2 = board.getActivePlayer();
            if(!player1.getName().equals(player2.getName())){
                equals = false;
                break;
            }
        }
        return equals;
    }

}
