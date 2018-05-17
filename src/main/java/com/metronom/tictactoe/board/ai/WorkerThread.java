package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;

import java.util.concurrent.Callable;

// Thread class
public class WorkerThread implements Callable<Integer> {

    private String name;
    private final TreeNode node;

    public WorkerThread(TreeNode node) {
        this.name=name;
        this.node = node;
    }



    @Override
    public Integer call() throws Exception {
        int result = 0;
        try {
            result = node.calculateMovement(1);
        } catch (InvalidFormatMovementException e) {
            e.printStackTrace();
        }
        return result;
    }
}
