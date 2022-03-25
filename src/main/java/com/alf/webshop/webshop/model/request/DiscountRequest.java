package com.alf.webshop.webshop.model.request;

import com.alf.webshop.webshop.entity.Category;
import com.alf.webshop.webshop.entity.Gender;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class DiscountRequest {
    @NotNull(message = "Discount code cannot be null!")
    @NotBlank(message = "Discount code cannot be blank!")
    String discountCode;

    List<Long> itemIds;

    List<Category> categories;

    Gender gender;
}
