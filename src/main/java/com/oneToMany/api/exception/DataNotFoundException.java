package com.oneToMany.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message){
        super(message);
    }
}
