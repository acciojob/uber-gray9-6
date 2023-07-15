package com.driver.exception;

public class CustomerAlreadyExists extends RuntimeException{
    public CustomerAlreadyExists(String message) {
        super(message);
    }
}
