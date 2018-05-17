package com.metronom.tictactoe.board;

import com.metronom.tictactoe.player.PlayerInterface;

import java.io.Serializable;

/**
 * Represent one movement for one player
 */
public class Move implements Serializable{
    private final int y;
    private final int x;
    private final PlayerInterface player;

    public Move(int x, int y, PlayerInterface player){
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }


    public PlayerInterface getPlayer() {
        return player;
    }
}
