package com.alf.webshop.webshop.model.response;

import com.alf.webshop.webshop.entity.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final double price;
    private final Color color;
    private final Gender gender;
    private final Size size;
    private final int sku;
    private final Category category;
    private final Discount discount;
    private final List<Image> images;
    private final List<Cart> carts;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.color = item.getColor();
        this.gender = item.getGender();
        this.size = item.getSize();
        this.sku = item.getSku();
        this.category = item.getCategory();
        this.discount = item.getDiscount();
        this.images = item.getImages();
        this.carts = item.getCarts();

        // so the response won't contain unnecessary fields (and an infinite loop)
        for (Cart cart : this.carts) {
            cart.setItems(null);
        }
    }

    public static List<ItemResponse> fromItemList(List<Item> items) {
        List<ItemResponse> itemsResponse = new ArrayList<>();
        for (Item item : items) {
            itemsResponse.add(new ItemResponse(item));
        }
        return itemsResponse;
    }
}
