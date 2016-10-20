package com.alfred.chowder.exception;

/**
 * Created by Alfred on 16/10/20.
 */

public class ApiException extends RuntimeException {
    private int resultCode;
    private String resutlMessage;

    public ApiException(int resultCode,String resutlMessage){
//        this()
    }
}
