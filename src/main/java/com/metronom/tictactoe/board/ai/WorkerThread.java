package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;

import java.util.concurrent.Callable;

// Thread class
public class WorkerThread implements Callable<Integer> {

    private String name;
    private final Node node;

    public WorkerThread(String name, Node node) {
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
