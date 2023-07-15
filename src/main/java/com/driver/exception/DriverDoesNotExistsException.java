package com.driver.exception;


public class DriverDoesNotExistsException extends RuntimeException {
    public DriverDoesNotExistsException(String message) {
        super(message);
    }
}
