package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "discount")
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Discount code cannot be null")
    @NotBlank(message = "Discount code cannot be blank")
    @Column(name = "code")
    private String code;

    @NotNull(message = "Discount description cannot be null")
    @NotBlank(message = "Discount description cannot be blank")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Discount percent cannot be null")
    @Min(1)
    @Max(100)
    @Column(name = "discount_percent")
    private int discountPercent;

    @NotNull(message = "Discount end date cannot be null")
    @Column(name = "end_date")
    private Date endDate;
}
