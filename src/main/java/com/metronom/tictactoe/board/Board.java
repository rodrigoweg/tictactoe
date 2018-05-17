package com.metronom.tictactoe.board;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.player.*;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.enums.GameOption;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Serializable {

    private Cell[][] cells;
    private int size;
    private Move lastMovement;
    private int freeCells;
    private PlayerInterface winner;
    private int activePlayerId;
    private List<PlayerInterface> players = new ArrayList<>();

    public Board(int size) {
        ConsoleUtility.logDebug(Const.BOARD_CREATED + size);
        this.size = size;
        this.freeCells = size * size;
        cells = new Cell[size][size];
    }

    public void initPlayers(GameOption gameOption, String human1Character, String human2Character, String computerCharacter) {
        players.clear();

        switch (gameOption) {
            case USERVSUSER:
                players.add(new Human(Const.PLAYER1, human1Character.charAt(0)));
                players.add(new Human(Const.PLAYER2, human2Character.charAt(0)));
                break;
            case USERVSCOMPUTER:
                players.add(new Human(Const.PLAYER1, human1Character.charAt(0)));
                players.add(new Computer(Const.COMPUTER, computerCharacter.charAt(0)));
                break;
            case USERSVSCOMPUTER:
                players.add(new Human(Const.PLAYER1, human1Character.charAt(0)));
                players.add(new Human(Const.PLAYER2, human2Character.charAt(0)));
                players.add(new Computer(Const.COMPUTER, computerCharacter.charAt(0)));
                break;
            case COMPUTERVSCOMPUTER:
                players.add(new Computer(Const.COMPUTER, computerCharacter.charAt(0)));
                players.add(new Computer(Const.COMPUTER2, human1Character.charAt(0)));
                //This option is for debug purpose, so we want always to start with the same player
                activePlayerId = 0;
                break;
        }
        //For ComputerVSComputer, gameoption is hardcoded.
        if (!gameOption.equals(GameOption.COMPUTERVSCOMPUTER)) {
            activePlayerId = (new Random()).nextInt(players.size());
        }
        ConsoleUtility.logProd(Const.FIRST_PLAYER + players.get(activePlayerId).getName());
    }

    public void paint(String severity) {
        ConsoleUtility.log("", severity);
        ConsoleUtility.log(Const.STATUS_GAME, severity);
        int rows = cells.length;
        int cols = cells[0].length;
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                ConsoleUtility.logBoard("" + (cells[row][col] != null ? cells[row][col].getValue() : " "), severity);
                if (col < cols - 1) ConsoleUtility.logBoard("|", severity);
            }
            ConsoleUtility.log("", severity);
        }
    }

    public void paintCoordinates() {
        if (size > 0) {
            int rows = cells.length;
            int cols = cells[0].length;
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    System.out.print(row + " " + col);   // each cell paints itself
                    if (col < cols - 1) System.out.print("|");
                }
                System.out.println();
            }
        } else {
            ConsoleUtility.error(Const.INIT_BOARD);
        }
    }

    public void doMovement(Move move) throws InvalidFormatMovementException {
        lastMovement = move;
        validateMove(move);
        Cell cell = new Cell(move.getPlayer().getCharacter());
        cells[move.getX()][move.getY()] = cell;
        freeCells--;
    }

    public StatusGame evaluateBoard() {
        boolean lineCompleted = isLineCompleted();
        if (lineCompleted) {
            winner = lastMovement.getPlayer();
            return StatusGame.FINISH;
        }
        if (isGameCompleted()) {
            return StatusGame.FINISH;
        }
        return StatusGame.NEXT_MOVE;
    }

    public boolean isGameCompleted() {
        return freeCells == 0;
    }

    public PlayerInterface getActivePlayer() {
        if (players != null) {
            return players.get(activePlayerId);
        }
        ConsoleUtility.error("players is null and activePlayerId: " + activePlayerId);
        return null;
    }

    public PlayerInterface getWinner() {
        return winner;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    protected void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    ;

    public Move getLastMovement() {
        return lastMovement;
    }

    protected void setLastMovement(Move move) {
        this.lastMovement = move;
    }

    public int getFreeCells() {
        return freeCells;
    }

    protected void setFreeCells(int freeCells) {
        this.freeCells = freeCells;
    }

    protected void setWinner(PlayerInterface winner) {
        this.winner = winner;
    }

    public int getActivePlayerId() {
        return activePlayerId;
    }

    protected void setActivePlayerId(int activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public List<PlayerInterface> getPlayers() {
        return players;
    }

    protected void setPlayers(List<PlayerInterface> players) {
        this.players = players;
    }


    private void validateMove(Move move) throws InvalidFormatMovementException {
        if (move.getX() < 0 || move.getX() >= this.size ||
                move.getY() < 0 || move.getY() >= this.size) {
            throw new InvalidFormatMovementException(Const.COORDINATES_OUT_BOUNDARIES + (size - 1));
        }

        if (cells[move.getX()][move.getY()] != null) {
            throw new InvalidFormatMovementException(Const.CELL_ALREADY_USED);
        }
    }

    private boolean isLineCompleted() {
        if (existVerticalLine() || existHorizontalLine() || existDiagonalLine()) {
            return true;
        }
        return false;
    }

    private boolean existVerticalLine() {
        //Search botton - up until
        boolean existLine = false;
        for (int x = 0; x < size; x++) {
            existLine = true;
            for (int y = 0; y < size; y++) {
                if (cells[x][y] == null ||
                        !cells[x][y].getValue().equals(lastMovement.getPlayer().getCharacter())) {
                    existLine = false;
                    break;
                }
            }
            if (existLine) {
                //Line found!!!
                paint(Const.DEBUG);
                ConsoleUtility.logDebug(Const.VERTICAL_LINE_FOUND + x);
                break;
            }
        }
        return existLine;
    }

    private boolean existHorizontalLine() {
        boolean existLine = false;
        for (int y = 0; y < size; y++) {
            existLine = true;
            for (int x = 0; x < size; x++) {
                if (cells[x][y] == null ||
                        !cells[x][y].getValue().equals(lastMovement.getPlayer().getCharacter())) {
                    existLine = false;
                    break;
                }
            }
            if (existLine) {
                //Line found!!!
                ConsoleUtility.logDebug(Const.HORIZONTAL_LINE_FOUND + y);
                break;
            }
        }
        return existLine;
    }

    private boolean existDiagonalLine() {
        boolean existLineLeft = true;
        boolean existLineRight = true;
        for (int diagonalIndex = 0; diagonalIndex < size; diagonalIndex++) {

            //Left to right diagonal
            if (cells[diagonalIndex][diagonalIndex] == null ||
                    !cells[diagonalIndex][diagonalIndex].getValue().equals(lastMovement.getPlayer().getCharacter())) {
                existLineLeft = false;
            }
            //Right to Left diagonal
            int coordinate_x = (size - 1) - diagonalIndex;
            if (cells[coordinate_x][diagonalIndex] == null ||
                    !cells[coordinate_x][diagonalIndex].getValue().equals(lastMovement.getPlayer().getCharacter())) {
                existLineRight = false;
            }
        }

        return existLineLeft || existLineRight;
    }

    public void updateActivePlayerToNextPlayer() {
        activePlayerId = (activePlayerId + 1) % players.size();
        ConsoleUtility.logDebug("Now is time to play for: " + players.get(activePlayerId).getName());
    }

    public Board getCopy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Board) ois.readObject();
        } catch (Exception e) {
            ConsoleUtility.logDebug(e.toString());
            return null;
        }
    }

}
