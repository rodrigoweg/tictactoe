package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;

import java.util.concurrent.Callable;

/**
 * This class will execute in an independent thread the execution of the node
 */
public class WorkerThread implements Callable<Integer> {

    private final TreeNode node;

    /**
     * Node to be processed
     * @param node
     */
    public WorkerThread(TreeNode node) {
        this.node = node;
    }

    /**
     * This method is executed when the thead starts
     * @return return the node score
     */
    @Override
    public Integer call(){
        int result = 0;
        try {
            result = node.calculateMovement(1);
        } catch (InvalidFormatMovementException e) {
            e.printStackTrace();
        } catch (TictactoeExcepiton tictactoeExcepiton) {
            tictactoeExcepiton.printStackTrace();
        }
        return result;
    }
}
