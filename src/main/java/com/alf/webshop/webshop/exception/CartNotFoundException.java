package com.alf.webshop.webshop.exception;

public class CartNotFoundException extends Exception {
    public CartNotFoundException() {
        super("Cart could not be found!");
    }
}
