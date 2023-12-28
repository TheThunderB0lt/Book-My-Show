package com.driver.BookMyShow.exceptions;

public class UnAuthorized extends RuntimeException{
    public UnAuthorized(String msg) {
        super(msg);
    }
}
