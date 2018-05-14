package com.metronom.tictactoe.board.ai;

import com.metronom.tictactoe.exceptions.InvalidFormatMovementException;
import com.metronom.tictactoe.board.Move;
import com.metronom.tictactoe.utils.ConsoleUtility;
import com.metronom.tictactoe.utils.Const;
import com.metronom.tictactoe.utils.Maths;
import com.metronom.tictactoe.utils.enums.StatusGame;

import java.util.ArrayList;
import java.util.List;

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
        int childNumber = 0;
        for (AIBoard nextBoard : possibleMovement) {
            nextBoard.updateActivePlayerToNextPlayer();
            Node child = new Node(nextBoard);
            Move move = nextBoard.getLastMovement();
            int childBoard = child.calculateMovement(treeLevel);

            if(treeLevel == 1){
                if(boardAI.getMove() == null || childBoard > boardAI.getScore()){
                    boardAI.setMove(possibleMovement.get(childNumber).getMove());
                    boardAI.setScore(childBoard);
                }
                childNumber++;
                ConsoleUtility.logDebug("LEVEL 1 ("+childNumber+"/"+possibleMovement.size()+")"+(treeLevel)+"-------------------------------> "+childBoard);
            }else{
                levelScore = levelScore + childBoard;
                childNumber++;
                ConsoleUtility.logDebug("LEVEL ("+childNumber+"/"+possibleMovement.size()+")"+(treeLevel)+" -> "+levelScore);
            }

            /*if (bestScoredBoard == null || bestScoredBoard.getScore() < childBoard.getScore()) {

                bestScoredBoard = childBoard;
                bestScoredBoard.setMove(move);
            } else {
                //Adding random to be less predictable
                if (bestScoredBoard.getScore() == childBoard.getScore()) {
                    int random100 = (new Random()).nextInt(100);
                    if (random100 > 50) {
                        bestScoredBoard = childBoard;
                        bestScoredBoard.setMove(move);
                    }
                }
            }*/
            //We cut some branches when apparently is good move
            /*if(childBoard.getScore() > Const.THRESHOLD_GOOD*boardAI.getBoardAI().getSize()){
                bestScoredBoard = childBoard;
                bestScoredBoard.setMove(move);
                bestScoredBoard.setScore(bestScoredBoard.getScore()+childBoard.getScore());
                return bestScoredBoard;
            }*/


        }

        return levelScore;
    }

    private int getMaxLevelOfThiking() {
        return 2 * (Const.LEVEL_OF_THINKING -boardAI.getSize());
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
