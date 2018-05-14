package com.metronom.tictactoe.player;

import java.io.Serializable;

public abstract class Player implements PlayerInterface, Serializable{
    protected String name;
    protected Character character;

    public Player(String name, Character c){
        this.name = name;
        this.character = c;
    }

    public Character getCharacter() {
        return character;
    }

    public String getName(){
        return name;
    }
}
