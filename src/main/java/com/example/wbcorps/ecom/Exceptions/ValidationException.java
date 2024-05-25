package com.example.wbcorps.ecom.Exceptions;

public class ValidationException extends RuntimeException{
    public  ValidationException(String message ){
        super(message);
    }
}
