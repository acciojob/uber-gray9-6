package com.driver.exception;

public class CustomerDoesNotExistsException extends RuntimeException{
    public CustomerDoesNotExistsException(String message) {
        super(message);
    }
}
