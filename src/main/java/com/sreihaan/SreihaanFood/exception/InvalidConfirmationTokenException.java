package com.sreihaan.SreihaanFood.exception;

public class InvalidConfirmationTokenException extends RuntimeException {

    public InvalidConfirmationTokenException()
    {
        super();
    }

    public InvalidConfirmationTokenException(String message)
    {
        super(message);
    }

}
