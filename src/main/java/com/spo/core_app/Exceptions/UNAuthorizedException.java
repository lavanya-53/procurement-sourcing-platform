package com.spo.core_app.Exceptions;

public class UNAuthorizedException extends RuntimeException{
    public UNAuthorizedException(String message){
        super(message);
    }
}
