package com.sreihaan.SreihaanFood.exception;

public class InvalidPasswordResetToken extends RuntimeException{

    public InvalidPasswordResetToken()
    {
        super();
    }

    public InvalidPasswordResetToken(String message)
    {
        super(message);
    }

}
