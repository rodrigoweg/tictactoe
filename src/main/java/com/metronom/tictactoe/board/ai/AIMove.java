package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.exceptions.BoardStatusException;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.player.PlayerInterface;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;

/**
 * Class in charge of organize the calculation of the AI Movement
 */
public class AIMove {

    private static boolean processing;

    /**
     *
     * @param board Current board in the game
     * @param player Player that should move
     * @return Next move that will bi executed
     * @throws InvalidFormatMovementException If the movement is not into the margins, this exception is triggered
     * @throws BoardStatusException The board should be ready for next movement otherwise this exception is triggered
     */
    public static Move getNextCleverMovement(Board board, PlayerInterface player) throws InvalidFormatMovementException, BoardStatusException, TictactoeExcepiton {
        if (board.isGameCompleted()) {
            throw new BoardStatusException(Const.BOARD_COMPLETED);
        }
        // this variable will manage the printing of dots when the computer is thinking
        processing = true;
        printLoading();
        ConsoleUtility.logProd(player.getName()+" thinking("+player.getCharacter()+")...");
        //We work with a copy of the board
        Board boardCloned = board.getCopy();
        AIBoard boardAI = new AIBoard(boardCloned);
        boardAI.setPlayerMoving(player);
        //The tree will take the decision for next movement.
        TreeNode tree = new TreeNode(boardAI);
        tree.calculateMovement(Const.LEVEL_ZERO);
        processing = false;
        return tree.getBoardAI().getNextMove();
    }

    /**
     * This method open a thread and print dots when the computer is thinking
     */
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
