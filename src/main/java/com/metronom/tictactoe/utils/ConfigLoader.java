package com.metronom.tictactoe.utils;

import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.utils.enums.GameOption;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigLoader {

    /**
     * This method load a property file for the classpath
     *
     * @return
     */
    static public Properties loadProperties() throws LoadPropertiesException {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            //Load Properties with file name hardcoded in the class const
            prop.load(ConfigLoader.class.getClassLoader().getResourceAsStream(Const.PROPERTY_FILE_NAME));
            if (prop == null) {
                throw new LoadPropertiesException(Const.PROPERTIES_FILE_NOT_FOUND);
            }
            if (validProperties(prop)) {
                //get the properties values and print it out
                ConsoleUtility.logProd("Properties values:");
                ConsoleUtility.logProd(Const.PROPERTY_PLAYGROUND_SIZE+ ": " + prop.getProperty(Const.PROPERTY_PLAYGROUND_SIZE));
                ConsoleUtility.logProd(Const.PROPERTY_GAME_OPTION+ ": " + prop.getProperty(Const.PROPERTY_GAME_OPTION));
                ConsoleUtility.logProd(Const.PROPERTY_CHARACTER_PLAYER1+ ": " + prop.getProperty(Const.PROPERTY_CHARACTER_PLAYER1));
                ConsoleUtility.logProd(Const.PROPERTY_CHARACTER_PLAYER2+ ": " + prop.getProperty(Const.PROPERTY_CHARACTER_PLAYER2));
                ConsoleUtility.logProd(Const.PROPERTY_CHARACTER_COMPUTER+ ": " + prop.getProperty(Const.PROPERTY_CHARACTER_COMPUTER));
            } else {
                throw new LoadPropertiesException(Const.PROPERTIES_NOT_VALID);
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new LoadPropertiesException(Const.PROPERTIES_FILE_ERROR);
                }
            }

        }
        return prop;
    }

    private static boolean validProperties(Properties prop) {
        boolean validProperties = true;
        String size = prop.getProperty(Const.PROPERTY_PLAYGROUND_SIZE);
        String gameOption = prop.getProperty(Const.PROPERTY_GAME_OPTION);
        String p1 = prop.getProperty(Const.PROPERTY_CHARACTER_PLAYER1);
        String p2 = prop.getProperty(Const.PROPERTY_CHARACTER_PLAYER2);
        String comp = prop.getProperty(Const.PROPERTY_CHARACTER_COMPUTER);

        String logLevel = prop.getProperty(Const.PROPERTY_LOG_LEVEL);
        ConsoleUtility.setLogLevel(logLevel);

        // Size validation
        if (size == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_PLAYGROUND_SIZE);
            validProperties = false;
        } else {
            try {
                int sizeInt = Integer.parseInt(size);
                if (sizeInt < Const.MIN_PLAYGROUND_SIZE || sizeInt > Const.MAX_PLAYGROUND_SIZE) {
                    ConsoleUtility.error(Const.SIZE_INVALID + sizeInt);
                    validProperties = false;
                }
            } catch (NumberFormatException e) {
                //size is not a number
                ConsoleUtility.error(Const.SIZE_NOT_NUMBER);
                validProperties = false;
            }
        }

        // Option validation
        if (gameOption == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_GAME_OPTION);
            validProperties = false;
        } else {
            GameOption gameOptionEnum = GameOption.valueOf(gameOption);
            if(gameOptionEnum==null){
                //size is not a number
                ConsoleUtility.error(Const.OPTION_INVALID);
                validProperties = false;
            }
        }
        // P1 validation
        if (p1 == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_PLAYER1);
            validProperties = false;
        } else {
            if (p1.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + p1 + "' for player1");
                validProperties = false;
            }
        }

        // P1 validation
        if (p2 == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_PLAYER2);
            validProperties = false;
        } else {
            if (p2.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + p2 + "' for player2");
                validProperties = false;
            }
        }

        // P1 validation
        if (comp == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_COMPUTER);
            validProperties = false;
        } else {
            if (comp.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + comp + "' for computer");
                validProperties = false;
            }
        }

        return validProperties;

    }
}
