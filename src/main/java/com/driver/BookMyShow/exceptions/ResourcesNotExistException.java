package com.driver.BookMyShow.exceptions;

public class ResourcesNotExistException extends RuntimeException{
    public ResourcesNotExistException(String msg) {
        super(msg);
    }
}
