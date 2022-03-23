package com.alf.webshop.webshop.exception;

public class UsernameIsTakenException extends Exception {
    public UsernameIsTakenException(String username) {
        super("This username (" + username + ") is taken!");
    }
}
