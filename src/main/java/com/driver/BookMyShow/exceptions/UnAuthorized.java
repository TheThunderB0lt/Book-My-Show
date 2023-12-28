package com.driver.BookMyShow.exceptions;

public class UnAuthorized extends RuntimeException{
    public UnAuthorized(String message) {
        super(message);
    }
}
