package com.me.budgetbackend.exceptions;

public class AdminAlreadyExistException extends RuntimeException{
    public AdminAlreadyExistException(String message)
    {
        super(message);
    }
}
