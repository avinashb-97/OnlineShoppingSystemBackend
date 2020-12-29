package com.sreihaan.SreihaanFood.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException()
    {
        super();
    }

    public CategoryNotFoundException(String message)
    {
        super(message);
    }

}
