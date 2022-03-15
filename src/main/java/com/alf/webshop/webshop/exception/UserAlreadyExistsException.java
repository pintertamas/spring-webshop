package com.alf.webshop.webshop.exception;

import com.alf.webshop.webshop.entity.User;

public class UserAlreadyExistsException extends Exception {
    User user;

    public UserAlreadyExistsException(User user) {
        super("User already exists: " + user);
        this.user = user;
    }

    public User getExistingUser() {
        return this.user;
    }
}
