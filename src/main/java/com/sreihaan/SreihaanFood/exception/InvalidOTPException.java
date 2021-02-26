package com.sreihaan.SreihaanFood.exception;

public class InvalidOTPException extends RuntimeException{

    public InvalidOTPException()
    {
        super();
    }

    public InvalidOTPException(String message)
    {
        super(message);
    }
}
