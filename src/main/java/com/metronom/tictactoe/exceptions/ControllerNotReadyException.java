package com.metronom.tictactoe.exceptions;

import com.metronom.tictactoe.utils.Const;

public class ControllerNotReadyException extends Throwable {
    public ControllerNotReadyException(){
        super(Const.CONTROLLER_NOT_INITIATED);
    }
}
