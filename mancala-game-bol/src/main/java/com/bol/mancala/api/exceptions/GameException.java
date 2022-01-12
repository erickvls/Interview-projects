package com.bol.mancala.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameException extends Exception{
    public GameException(String message){
        super(message);
    }
}

