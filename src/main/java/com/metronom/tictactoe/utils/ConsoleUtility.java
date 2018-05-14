package com.metronom.tictactoe.utils;

public class ConsoleUtility {
    //By default debug messages are ignored
    static String logLevel = Const.PROD;

    public static void error(String msn) {
        System.out.println("Error: "+msn);
    }

    public static void logProd(String msn) {
            System.out.println(msn);
    }

    public static void logDebug(String msn) {
        if(logLevel.equals(Const.DEBUG))
        System.out.println("Debug -> "+msn);
    }

    public static void logInfo(String msn) {
        if(logLevel.equals(Const.INFO))
            System.out.println("Info -> "+msn);
    }

    public static void logBoard(String msn, String severity) {
        if(Const.PROD.equals(severity)){
            System.out.print(msn);
        }else{
            if(logLevel.equals(severity)){
                System.out.print(msn);
            }
        }
    }
    public static void log(String msn, String severity) {
        if(Const.PROD.equals(severity)){
            System.out.println(msn);
        }else{
            if(logLevel.equals(severity)){
                System.out.println(msn);
            }
        }
    }

    public static void setLogLevel(String severity) {
        if(severity!=null && (Const.DEBUG.equals(severity)
                || Const.INFO.equals(severity))){
            logLevel = severity;
        }
    }
}
