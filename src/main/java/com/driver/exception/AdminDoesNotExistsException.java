package com.driver.exception;

public class AdminDoesNotExistsException extends RuntimeException{
    public AdminDoesNotExistsException(String message) {
        super(message);
    }
}
