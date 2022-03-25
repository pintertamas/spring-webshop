package com.alf.webshop.webshop.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(Long id) {
        super("Item with id: " + id + " could not be found!");
    }
}
