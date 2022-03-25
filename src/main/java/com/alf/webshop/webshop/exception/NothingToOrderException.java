package com.alf.webshop.webshop.exception;

public class NothingToOrderException extends Exception {
    public NothingToOrderException(Long id) {
        super("You dont have anything in your cart (id: " + id + "), or something went wrong");
    }
}
