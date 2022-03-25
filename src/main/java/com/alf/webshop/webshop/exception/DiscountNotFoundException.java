package com.alf.webshop.webshop.exception;

public class DiscountNotFoundException extends Exception {
    public DiscountNotFoundException(String code) {
        super("Discount with code: " + code + " could not be found!");
    }
}
