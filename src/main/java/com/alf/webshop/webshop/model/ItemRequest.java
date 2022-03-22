package com.alf.webshop.webshop.model;

import com.alf.webshop.webshop.entity.Category;
import com.alf.webshop.webshop.entity.Color;
import com.alf.webshop.webshop.entity.Gender;
import com.alf.webshop.webshop.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequest {
    String name;
    String description;
    double price;
    Color color;
    Gender gender;
    Category category;
    ArrayList<String> images;
    Size size;
    int sku;
}
