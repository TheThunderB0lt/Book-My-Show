package com.driver.BookMyShow.exceptions;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
