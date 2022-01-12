package com.bol.mancala.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PitNotFoundException extends Exception{
    public PitNotFoundException(String message){
        super(message);
    }
}

