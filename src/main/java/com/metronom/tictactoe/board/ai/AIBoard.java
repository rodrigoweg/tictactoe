package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.StatusGame;

/**
 * This class is an extension of the board with next move calculated, the player moving and the score calculated by the AI.
 */
public class AIBoard extends Board {
    private int score;
    private Move nextMove;
    private PlayerInterface playerMoving;

    /**
     * Copy constructor
     *
     * @param board Board to be copied
     */
    public AIBoard(Board board) {
        super(board.getSize());
        copyBoardFields(board);
    }

    /**
     * Copy board parameters into the fields of this class
     *
     * @param board
     */
    private void copyBoardFields(Board board) {
        setCells(board.getCells());
        setLastMovement(board.getLastMovement());
        setFreeCells(board.getFreeCells());
        setWinner(board.getWinner());
        setActivePlayerId(board.getActivePlayerId());
        setPlayers(board.getPlayers());
    }


    /**
     * @return The score of this board based on its status
     */
    public int getPunctuationBoard() {
        StatusGame status = evaluateBoard();
        if (getWinner() == null) {
            setScore(0);
        } else {
            if (getWinner().getName().equals(Const.COMPUTER)) {
                setScore(10);
            } else {
                setScore(-20);
            }
        }
        return getScore();
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public PlayerInterface getPlayerMoving() {
        return playerMoving;
    }

    public void setPlayerMoving(PlayerInterface playerMoving) {
        this.playerMoving = playerMoving;
    }
}
