package com.driver.exception;

public class CustomerListIsEmptyException extends RuntimeException{
    public CustomerListIsEmptyException(String message) {
        super(message);
    }
}
