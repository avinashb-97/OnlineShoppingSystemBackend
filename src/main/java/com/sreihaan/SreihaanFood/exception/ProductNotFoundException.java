package com.sreihaan.SreihaanFood.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException()
    {
        super();
    }

    public ProductNotFoundException(String message)
    {
        super(message);
    }

}
