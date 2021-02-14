package com.sreihaan.SreihaanFood.exception;

public class InvalidCategoryException extends RuntimeException{

    public InvalidCategoryException()
    {
        super();
    }

    public InvalidCategoryException(String message)
    {
        super(message);
    }
}
