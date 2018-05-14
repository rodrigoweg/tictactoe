package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.ArrayList;

public class AIBoard extends Board{
    private int score;
    private Move move;
    private PlayerInterface playerMoving;

    public AIBoard(Board board) {
        super(board.getSize());
        copyBoardFields(board);
    }

    private void copyBoardFields(Board board) {
        setCells(board.getCells());
        setLastMovement(board.getLastMovement());
        setFreeCells(board.getFreeCells());
        setWinner(board.getWinner());
        setActivePlayerId(board.getActivePlayerId());
        setPlayers(board.getPlayers());
    }


    public int getPunctuationBoard() {
        StatusGame status = evaluateBoard();
        switch (status) {
            case FINISH:
                if (getWinner() == null) {
                    setScore(0);
                } else {
                    if (getWinner().getName().equals(Const.COMPUTER)) {
                        setScore(10);
                    } else {
                        setScore(-20);
                    }
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

    public void setMove(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public PlayerInterface getPlayerMoving() {
        return playerMoving;
    }

    public void setPlayerMoving(PlayerInterface playerMoving) {
        this.playerMoving = playerMoving;
    }
}
