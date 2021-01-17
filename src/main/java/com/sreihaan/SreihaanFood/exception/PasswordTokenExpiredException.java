package com.sreihaan.SreihaanFood.exception;

public class PasswordTokenExpiredException extends RuntimeException {

    public PasswordTokenExpiredException()
    {
        super();
    }

    public PasswordTokenExpiredException(String message)
    {
        super(message);
    }

}
