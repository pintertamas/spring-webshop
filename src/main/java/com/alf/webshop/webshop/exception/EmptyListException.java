package com.alf.webshop.webshop.exception;

public class EmptyListException extends Exception {
    public EmptyListException() {
        super("No list elements found!");
    }
}
