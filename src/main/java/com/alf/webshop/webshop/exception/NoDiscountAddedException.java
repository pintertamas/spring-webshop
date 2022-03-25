package com.alf.webshop.webshop.exception;

public class NoDiscountAddedException extends Exception {
    public NoDiscountAddedException() {
        super("Could not add discount to any item!");
    }
}
