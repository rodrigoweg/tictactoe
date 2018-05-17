package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;

public class AIMove {

    private static boolean processing;

    public static Move getNextCleverMovement(Board board, PlayerInterface player) throws InvalidFormatMovementException, BoardStatusException {
        if (board.isGameCompleted()) {
            throw new BoardStatusException(Const.BOARD_COMPLETED);
        }
        processing = true;
        printLoading();
        ConsoleUtility.logProd(player.getName()+" thinking("+player.getCharacter()+")...");
        Board boardCloned = board.getCopy();
        AIBoard boardAI = new AIBoard(boardCloned);
        boardAI.setPlayerMoving(player);
        TreeNode tree = new TreeNode(boardAI);
        tree.calculateMovement(Const.LEVEL_ZERO);
        processing = false;
        return tree.getBoardAI().getNextMove();
    }

    private static void printLoading(){
        Thread newThread = new Thread() {
            public void run() {
                try {
                    while (processing) {
                        System.out.print(".");
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        };
        newThread.start();
    }
}
