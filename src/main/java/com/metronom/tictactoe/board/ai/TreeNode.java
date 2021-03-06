package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.exceptions.TictactoeExcepiton;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.MathsUtility;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class implement the nodes of the decision tree for the next movement
 */
public class TreeNode {

    private AIBoard boardAI;
    private List<TreeNode> children;

    /**
     * Constructor take the board as a base line
     *
     * @param boardAI Board with current status
     */
    public TreeNode(AIBoard boardAI) {
        this.boardAI = boardAI;
        children = new ArrayList<>();
    }

    /**
     * Recursive method that iterates the possible solutions. The first level in the tree is split and after  joined
     *
     * @param treeLevel Level of explore the tree
     * @return Puctuation for the movement
     * @throws InvalidFormatMovementException
     */
    public int calculateMovement(int treeLevel) throws InvalidFormatMovementException, TictactoeExcepiton {
        //Base case for recursive method 
        if (treeLevel >= getMaxLevelOfThiking() ||
                boardAI.isGameCompleted()
                || boardAI.evaluateBoard().equals(StatusGame.FINISH)
                ) {
            //It is a leave(boardAI complete) or there is a winner or number of movements processed is max
            updateIABoardStatus(treeLevel);
            ConsoleUtility.logInfo("The node below has this punctuation: "
                    + boardAI.getScore() + " for player: " + boardAI.getPlayerMoving().getName()
                    + " ( " + boardAI.getPlayerMoving().getCharacter() + " )");
            boardAI.paint(Const.INFO);
            ConsoleUtility.logDebug("LEVEL " + (treeLevel) + " -> " + boardAI.getScore());
            return boardAI.getScore();
        }
        //possiblemovement contains a list of boards with one extra movement from the original board
        List<AIBoard> possibleMovement = getNextBoards();
        ConsoleUtility.logDebug("Player for AI now is: " + boardAI.getActivePlayer().getName());
        treeLevel++;
        int levelScore = 0;
        // Objects required for managing the threads
        ExecutorService executorService = Executors.newFixedThreadPool(possibleMovement.size());
        List<Future<Integer>> futureResults = new ArrayList<Future<Integer>>(possibleMovement.size());

        //Lets iterate over each children to find best movement
        for (int childIndex = 0; childIndex < possibleMovement.size(); childIndex++) {
            AIBoard nextBoard = possibleMovement.get(childIndex);
            nextBoard.updateActivePlayerToNextPlayer();
            TreeNode child = new TreeNode(nextBoard);
            Move move = nextBoard.getLastMovement();

            if (treeLevel == 1) {
                //In the first level, each child is processed in a different thread and futureReulsts will collect the calculated values
                Callable<Integer> task = new WorkerThread(child);
                futureResults.add(executorService.submit(task));
            } else {
                int childBoard = child.calculateMovement(treeLevel);
                levelScore = levelScore + childBoard;
                ConsoleUtility.logDebug("LEVEL (" + childIndex + "/" + possibleMovement.size() + ")" + (treeLevel) + " -> " + levelScore);
            }
        }
        // If treelevel is 1, lets join all the results
        if (treeLevel == 1) {
            joinResultsFromThreads(possibleMovement, futureResults);
        }
        return levelScore;
    }

    /**
     * This method set into nextMovment of the boardAI the best movement
     *
     * @param possibleMovement List of boards with the possible movements
     * @param results          Score for each movement
     */
    private void joinResultsFromThreads(List<AIBoard> possibleMovement, List<Future<Integer>> results) throws InvalidFormatMovementException, TictactoeExcepiton {

        //possibleMovement and results contains the same order and with index it is possible to link them
        for (int childIndex = 0; childIndex < results.size(); childIndex++) {
            try {
                Future<Integer> result = results.get(childIndex);
                if (result == null) {
                    throw new InvalidFormatMovementException("Error getting score for children index: " + childIndex);
                }
                int childBoard = result.get();
                if (boardAI.getNextMove() == null || childBoard > boardAI.getScore()) {
                    boardAI.setNextMove(possibleMovement.get(childIndex).getNextMove());
                    boardAI.setScore(childBoard);
                }
                ConsoleUtility.logDebug("LEVEL 1 (" + childIndex + "/" + possibleMovement.size() + ")(1)-------------------------------> " + childBoard);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new TictactoeExcepiton("Error processing computer movement.");
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new TictactoeExcepiton("Error processing computer movement.");
            }
        }
    }

    private int getMaxLevelOfThiking() {
        return Const.LEVEL_OF_THINKING == 100 ? boardAI.getSize() * boardAI.getSize() : 2;
    }

    public AIBoard getBoardAI() {
        return boardAI;
    }

    private TreeNode getChildren(int index) {
        if (index >= children.size()) {
            return null;
        }
        return children.get(index);
    }

    /**
     * Evaluate the board and set the score
     *
     * @param level
     */
    private void updateIABoardStatus(int level) {
        StatusGame status = boardAI.evaluateBoard();
        switch (status) {
            case FINISH:
                if (boardAI.getWinner() == null) {
                    boardAI.setScore(0);
                } else {
                    int factor = (boardAI.getSize() * boardAI.getSize()) + 1;
                    if (boardAI.getWinner().getName().equals(boardAI.getPlayerMoving().getName())) {
                        boardAI.setScore(MathsUtility.factorial((factor - level)));
                    } else {
                        if (level == 2) {
                            boardAI.setScore(-999999999);
                        } else {
                            boardAI.setScore(-1 * MathsUtility.factorial((factor - level)));
                        }
                    }
                }

        }
    }

    /**
     * This method create one board for each empty cell. Each time put one more movement
     *
     * @return List of boards
     * @throws InvalidFormatMovementException If the movement is not valid this exception is thrown
     */
    private List<AIBoard> getNextBoards() throws InvalidFormatMovementException {
        List<AIBoard> boards = new ArrayList<>();
        for (int x = 0; x < boardAI.getSize(); x++) {
            for (int y = 0; y < boardAI.getSize(); y++) {
                if (boardAI.getCells()[x][y] == null) {
                    Move move = new Move(x, y, boardAI.getActivePlayer());
                    AIBoard nextBoard = new AIBoard(boardAI.getCopy());
                    nextBoard.doMovement(move);
                    nextBoard.setNextMove(move);
                    nextBoard.setPlayerMoving(boardAI.getPlayerMoving());
                    boards.add(nextBoard);
                }
            }
        }
        return boards;
    }
}
