package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.Maths;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Node {

    private AIBoard boardAI;
    private List<Node> children;

    public Node(AIBoard boardAI) {
        this.boardAI = boardAI;
        children = new ArrayList<>();
    }

    public int calculateMovement(int treeLevel) throws InvalidFormatMovementException {

        if (treeLevel >= getMaxLevelOfThiking() ||
                boardAI.isGameCompleted()
                || boardAI.evaluateBoard().equals(StatusGame.FINISH)
                ) {
            //It is a leave(boardAI complete) or there is a winner or number of movements processed is max
            updatePunctuationBoard(treeLevel);
            ConsoleUtility.logInfo("The node below has this punctuation: "
                    + boardAI.getScore() + " for player: " + boardAI.getPlayerMoving().getName()
                        +" ( "+ boardAI.getPlayerMoving().getCharacter() + " )");
            boardAI.paint(Const.INFO);
            ConsoleUtility.logDebug("LEVEL "+(treeLevel)+" -> "+ boardAI.getScore());
            return boardAI.getScore();
        }

        List<AIBoard> possibleMovement = getNextBoards();
        ConsoleUtility.logDebug("Player for AI now is: " + boardAI.getActivePlayer().getName());
        treeLevel++;
        int levelScore = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(possibleMovement.size());
        List<Future<Integer>> results = new ArrayList<Future<Integer>>(possibleMovement.size());
        for (int childIndex = 0; childIndex < possibleMovement.size(); childIndex++) {
            AIBoard nextBoard = possibleMovement.get(childIndex);
            nextBoard.updateActivePlayerToNextPlayer();
            Node child = new Node(nextBoard);
            Move move = nextBoard.getLastMovement();

            if(treeLevel == 1){
                Callable<Integer> task = new WorkerThread("Child_"+childIndex, child);
                results.add(executorService.submit(task));
            }else{
                int childBoard = child.calculateMovement(treeLevel);
                levelScore = levelScore + childBoard;
                ConsoleUtility.logDebug("LEVEL ("+childIndex+"/"+possibleMovement.size()+")"+(treeLevel)+" -> "+levelScore);
            }
        }
        if(treeLevel == 1){
            for (int childIndex = 0; childIndex < results.size(); childIndex++) {
                try {
                    int childBoard =  results.get(childIndex).get();
                    if(boardAI.getMove() == null || childBoard > boardAI.getScore()){
                        boardAI.setMove(possibleMovement.get(childIndex).getMove());
                        boardAI.setScore(childBoard);
                    }
                    ConsoleUtility.logDebug("LEVEL 1 ("+childIndex+"/"+possibleMovement.size()+")"+(treeLevel)+"-------------------------------> "+childBoard);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return levelScore;
    }

    private int getMaxLevelOfThiking() {
        //return Const.LEVEL_OF_THINKING;
        return Const.LEVEL_OF_THINKING == 100? boardAI.getSize()*boardAI.getSize() : 2;
    }

    public AIBoard getBoardAI() {
        return boardAI;
    }

    private Node getChildren(int index) {
        if (index >= children.size()) {
            return null;
        }
        return children.get(index);
    }

    private void updatePunctuationBoard(int level) {
        StatusGame status = boardAI.evaluateBoard();
        switch (status) {
            case FINISH:
                if (boardAI.getWinner() == null) {
                    boardAI.setScore(0);
                } else {
                    int factor = (boardAI.getSize() * boardAI.getSize())+1;
                    if (boardAI.getWinner().getName().equals(boardAI.getPlayerMoving().getName())) {
                        boardAI.setScore(Maths.factorial((factor-level)));
                    } else {
                        if(level == 2){
                            boardAI.setScore(-999999999);
                        }else{
                            boardAI.setScore(-2 * Maths.factorial((factor-level)));
                        }
                    }
                }

        }
    }

    private List<AIBoard> getNextBoards() throws InvalidFormatMovementException {
        List<AIBoard> boards = new ArrayList<>();
        for (int x = 0; x < boardAI.getSize(); x++) {
            for (int y = 0; y < boardAI.getSize(); y++) {
                if (boardAI.getCells()[x][y] == null) {
                    Move move = new Move(x, y, boardAI.getActivePlayer());
                    AIBoard nextBoard = new AIBoard(boardAI.getCopy());
                    nextBoard.doMovement(move);
                    nextBoard.setMove(move);
                    nextBoard.setPlayerMoving(boardAI.getPlayerMoving());
                    boards.add(nextBoard);
                }
            }
        }
        return boards;
    }
}
