package com.alf.webshop.webshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@AllArgsConstructor
public class CreateDiscountRequest {

    @NotNull(message = "Discount code cannot be null")
    @NotBlank(message = "Discount code cannot be blank")
    private String code;

    @NotNull(message = "Discount description cannot be null")
    @NotBlank(message = "Discount description cannot be blank")
    private String description;

    @NotNull(message = "Discount percent cannot be null")
    @Min(1)
    @Max(100)
    private int discountPercent;

    @NotNull(message = "Discount end date cannot be null")
    private Date endDate;
}
