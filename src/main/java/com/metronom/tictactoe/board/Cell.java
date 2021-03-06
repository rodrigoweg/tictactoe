package com.metronom.tictactoe.board;

import java.io.Serializable;

/**
 * Represents each cell of the board
 */
public class Cell implements Serializable {
    private Character value;

    public Cell(char character) {
        value = character;
    }


    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }
}
