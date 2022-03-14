package com.alf.webshop.webshop.exception;

public class UserCannotDeleteThemselfException extends Exception {
    public UserCannotDeleteThemselfException() {
        super("User cannot delete themself!");
    }
}
