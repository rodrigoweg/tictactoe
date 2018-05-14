package com.metronom.tictactoe.player;

import com.metronom.tictactoe.board.Board;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;

import java.util.Scanner;

public class Human extends Player {
    public Human(String name, Character character) {
        super(name,character);
    }

    @Override
    /**
     * The player study the board and generate a move that should be two ints separated by space
     */
    public Move move(Board board) throws InvalidFormatMovementException {
        board.paint(Const.PROD);
        ConsoleUtility.logProd(getName()+"("+getCharacter()+")"+Const.ENTER_NEXT_MOVE);
        // Scanner to read command-line input
        ConsoleUtility.logProd(Const.ENTER_X);
        Scanner scanner = new Scanner(System.in);
        String x = scanner.next();
        ConsoleUtility.logProd(Const.ENTER_Y);
        scanner = new Scanner(System.in);
        String y = scanner.next();
        Move move = validateMove(x+" "+y);
        return move;
    }

    /**
     *
     * @param move New move for the user
     * @throws InvalidFormatMovementException If the movement is null, length distinct 2 or not numeric is invalid
     */
    private Move validateMove(String move) throws InvalidFormatMovementException {
        if(move == null){
            ConsoleUtility.error("Move is null for human: "+name);
            throw new InvalidFormatMovementException(Const.ERROR_READING_MOVE);
        }
        try {
            String[] coordinates = move.split(" ");
            if(coordinates.length != 2){
                throw new InvalidFormatMovementException(Const.LENGTH_MOVE_INVALID);
            }
            int y = Integer.parseInt(coordinates[0]);
            int x = Integer.parseInt(coordinates[1]);
            return new Move(x,y, this);
        }catch (NumberFormatException e){
            ConsoleUtility.logDebug(e.getMessage());
            throw new InvalidFormatMovementException(Const.NUMERIC_MOVE_INVALID);
        }
    }
}
