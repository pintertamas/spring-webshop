package com.alf.webshop.webshop.exception;

import com.alf.webshop.webshop.entity.User;

public class UserCannotDeleteThemselfException extends Exception {
    User user;

    public UserCannotDeleteThemselfException(User user) {
        super("User cannot delete themself!");
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
