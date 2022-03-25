package com.alf.webshop.webshop.exception;

import com.alf.webshop.webshop.entity.Discount;

public class DiscountAlreadyExistsException extends Exception {
    Discount discount;

    public DiscountAlreadyExistsException(Discount discount) {
        super("Discount already exists: " + discount);
        this.discount = discount;
    }

    public Discount getExistingDiscount() {
        return this.discount;
    }
}
