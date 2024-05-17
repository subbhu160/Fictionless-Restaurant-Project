package com.vaanara.customer.exception;



@SuppressWarnings("serial")
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}