package com.alf.webshop.webshop.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User could not be found!");
    }
}
