package com.alf.webshop.webshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItem {
    public Long itemId;
    public int quantity;
}
