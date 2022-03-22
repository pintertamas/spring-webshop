package com.alf.webshop.webshop.model.response;

import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class CartResponse {
    private Long id;
    private int total;
    private List<Item> items;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.total = cart.getTotal();
        this.items = cart.getItems();

        // so the response won't contain unnecessary fields (and an infinite loop)
        for (Item item : items) {
            item.setCarts(null);
        }
    }

    public static List<CartResponse> fromCartList(List<Cart> carts) {
        List<CartResponse> cartResponses = new ArrayList<>();
        for (Cart cart : carts) {
            cartResponses.add(new CartResponse(cart));
        }
        return cartResponses;
    }
}
