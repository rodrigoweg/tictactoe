package com.metronom.tictactoe.utils;

public class Const {

    //ERRORS
    public static final String PROPERTIES_FILE_NOT_FOUND = "Properties file not found.";
    public static final String SIZE_NOT_NUMBER = "Playground size is not a number";
    public static final String SIZE_INVALID = "Playground size is not valid: ";
    public static final String CHARACTER_NOT_VALID = "Character is not valid: ";
    public static final String OPTION_INVALID = "Option game is not valid: ";
    public static final String PROPERTIES_NOT_VALID = "Properties are not valid. ";
    public static final String MISSING_PROPERTY = "Missing property: ";
    public static final String PROPERTIES_FILE_ERROR = "Properties file error. Stopping TicTacToe.";
    public static final String STOP_GAME = "Stopping game";
    public static final String LENGTH_MOVE_INVALID = "Length of coordinates should be 2.";
    public static final String NUMERIC_MOVE_INVALID = "Coordinates should be numeric.";
    public static final String ERROR_READING_MOVE = "Error reading move, please try again.";
    public static final String COORDINATES_OUT_BOUNDARIES = "Coordinates out of boundaries. Please enter move between 0 and ";
    public static final String CELL_ALREADY_USED = "Cell already in use. Please, chose another one.";
    public static final String INIT_BOARD = "Not possible to paint board. Please init before paint.";
    public static final String BOARD_COMPLETED = "I can not move, board is completed!";

    //PARAMETERS
    public static final int MIN_PLAYGROUND_SIZE = 0;
    public static final int MAX_PLAYGROUND_SIZE = 10;
    public static final String PLAYER1 = "player1";
    public static final String PLAYER2 = "player2";
    public static final String COMPUTER = "computer";
    public static final String COMPUTER2 = "computer2";
    public static final int LEVEL_ZERO = 0;
    public static final int LEVEL_OF_THINKING = 11;
    public static final int THRESHOLD_GOOD = 9;
    public static final int THRESHOLD_BAD = -20;

    //PROPERTIES
    public static final String PROPERTY_FILE_NAME = "application5.properties";
    public static final String PROPERTY_GAME_OPTION = "game_option";
    public static final String PROPERTY_PLAYGROUND_SIZE = "playground_size";
    public static final String PROPERTY_CHARACTER_PLAYER1 = "character_player1";
    public static final String PROPERTY_CHARACTER_PLAYER2 = "character_player2";
    public static final String PROPERTY_CHARACTER_COMPUTER = "character_computer";
    public static final String PROPERTY_LOG_LEVEL = "log_level";
    public static final String DEBUG = "debug";
    public static final String INFO = "info";
    public static final String PROD = "prod";

    //User communications
    public static final String GAME_START = "new Tic Tac Toe starts...";
    public static final String GAME_COMPLETED = "Game completed, you draw!";
    public static final String MOVEMENT_INCORRECT = "Movement incorrect.";
    public static final String ENTER_NEXT_MOVE = ", enter move:";
    public static final String FIND_BELOW_COORDINATES = "Please, find below the coordinates you should enter to play: ";
    public static final String LETS_PLAY = "Let's play!";
    public static final String ENTER_X = "Please enter X: ";
    public static final String ENTER_Y = "Please enter Y: ";
    public static final String STATUS_GAME = "Status of the game:";
    public static final String BOARD_CREATED = "Board created with size: ";
    public static final String VERTICAL_LINE_FOUND = "Vertical line found in X: ";
    public static final String HORIZONTAL_LINE_FOUND = "Horizontal Line found in y: ";
    public static final String FIRST_PLAYER = "First user to play is... ";
    public static final String CONGRATULATIONS = "Congratulations ";
    public static final String WINNER_MESSAGE = ", you are the winner.";

}
