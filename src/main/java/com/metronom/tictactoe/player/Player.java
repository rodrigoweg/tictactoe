package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;

import java.io.Serializable;

public abstract class Player implements PlayerInterface, Serializable{
    protected String name;
    protected Character character;

    public Player(String name, Character c){
        this.name = name;
        this.character = c;
    }

    /**
     *
     * @param coordinatesStr Coordinates where player wants to do the next move
     * @param size
     * @throws InvalidFormatMovementException If the movement is null, length distinct 2 or not numeric is invalid
     */
    protected Move validateMove(String coordinatesStr, int size) throws InvalidFormatMovementException {
        if(coordinatesStr == null){
            ConsoleUtility.error("Move is null for human: "+name);
            throw new InvalidFormatMovementException(Const.ERROR_READING_MOVE);
        }
        try {
            String[] coordinates = coordinatesStr.split(",");
            if(coordinates.length != 2 ){
                throw new InvalidFormatMovementException(Const.LENGTH_MOVE_INVALID);
            }

            int y = Integer.parseInt(coordinates[0]);
            int x = Integer.parseInt(coordinates[1]);
            if( x < 0 || x >= size || y < 0 || y >= size){
                throw new InvalidFormatMovementException(Const.COORDINATES_OUT_BOUNDARIES+(size-1));
            }

            return new Move(x,y, this);
        }catch (NumberFormatException e){
            ConsoleUtility.logDebug(e.getMessage());
            throw new InvalidFormatMovementException(Const.NUMERIC_MOVE_INVALID);
        }
    }

    public Character getCharacter() {
        return character;
    }

    public String getName(){
        return name;
    }
}
