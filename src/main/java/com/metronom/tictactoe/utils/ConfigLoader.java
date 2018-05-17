package com.metronom.tictactoe.utils;

import com.metronom.tictactoe.exceptions.LoadPropertiesException;
import com.metronom.tictactoe.utils.enums.GameOption;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigLoader {

    /**
     * This method load a property file from the classpath
     *
     * @return Properties object with all required properties and validated
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

    /**
     *
     * @param properties properties required to load the game
     * @return If all required properties are present or not
     */
    public static boolean validProperties(Properties properties) {
        boolean validProperties = true;
        String size = properties.getProperty(Const.PROPERTY_PLAYGROUND_SIZE);
        String gameOption = properties.getProperty(Const.PROPERTY_GAME_OPTION);
        String player1Character = properties.getProperty(Const.PROPERTY_CHARACTER_PLAYER1);
        String player2Character = properties.getProperty(Const.PROPERTY_CHARACTER_PLAYER2);
        String computerCharacter = properties.getProperty(Const.PROPERTY_CHARACTER_COMPUTER);

        String logLevel = properties.getProperty(Const.PROPERTY_LOG_LEVEL);
        ConsoleUtility.setLogLevel(logLevel);

        // Size validation
        if (size == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_PLAYGROUND_SIZE);
            validProperties = false;
        } else {
            try {
                int sizeInt = Integer.parseInt(size);
                //Checking if size is between required values
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

        // GameOption validation
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
        // Player1 character validation
        if (player1Character == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_PLAYER1);
            validProperties = false;
        } else {
            if (player1Character.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + player1Character + "' for player1");
                validProperties = false;
            }
        }

        // Player2 character validation
        if (player2Character == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_PLAYER2);
            validProperties = false;
        } else {
            if (player2Character.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + player2Character + "' for player2");
                validProperties = false;
            }
        }

        // ComputerCharacter validation
        if (computerCharacter == null) {
            //size is not a number
            ConsoleUtility.error(Const.MISSING_PROPERTY + Const.PROPERTY_CHARACTER_COMPUTER);
            validProperties = false;
        } else {
            if (computerCharacter.length() != 1) {
                ConsoleUtility.error(Const.CHARACTER_NOT_VALID + computerCharacter + "' for computer");
                validProperties = false;
            }
        }

        return validProperties;

    }
}
