package com.alf.webshop.webshop.exception;

import com.alf.webshop.webshop.entity.Cart;

public class CartNotFoundException extends Exception {
    Cart cart;

    public CartNotFoundException(Cart cart) {
        super("Cart could not be found!");
        this.cart = cart;
    }

    public Cart getCart() {
        return this.cart;
    }
}
