package com.alf.webshop.webshop.exception;

public class CartNotFoundException extends Exception {
    public CartNotFoundException(Long id) {
        super("Cart with id: " + id + " could not be found!");
    }
}
