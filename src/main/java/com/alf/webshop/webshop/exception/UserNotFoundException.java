package com.alf.webshop.webshop.exception;

public class UserNotFoundException extends Exception {
    Long userId;

    public UserNotFoundException(Long userId) {
        super("User with id: " + userId + " could not be found!");
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }
}
