package com.alf.webshop.webshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ItemRequest {
    Long creatorId;
    String name;
    String description;
    double price;
    ArrayList<String> images;

    public ItemRequest(Long creatorId, String name, String description, double price, ArrayList<String> images) {
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
    }
}
