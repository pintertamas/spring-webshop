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

    @NotNull(message = "Discount name cannot be null")
    @NotBlank(message = "Discount name cannot be blank")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Discount description cannot be null")
    @NotBlank(message = "Discount description cannot be blank")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Discount percent cannot be null")
    @NotBlank(message = "Discount percent cannot be blank")
    @Min(1)
    @Max(100)
    @Column(name = "discount_percent")
    private int discountPercent;

    @NotNull(message = "Discount end date cannot be null")
    @NotBlank(message = "Discount end date cannot be blank")
    @Column(name = "end_date")
    private Date endDate;
}
