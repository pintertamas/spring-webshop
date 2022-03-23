package com.alf.webshop.webshop.model.request;

import com.alf.webshop.webshop.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequest {
    @NotBlank(message = "Item name cannot be blank")
    String name;
    @NotBlank(message = "Item description cannot be blank")
    String description;
    @Min(value = 0, message = "Price cannot be negative")
    double price;
    Color color;
    Gender gender;
    Category category;
    ArrayList<String> images;
    Size size;
    @Min(value = -1, message = "Item sku number should be positive of -1 if you do not want to edit it")
    int sku;
}
