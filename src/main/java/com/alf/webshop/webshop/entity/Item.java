package com.alf.webshop.webshop.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Item")
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @NotNull(message = "Item name cannot be null")
    @NotBlank(message = "Item name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Item description cannot be null")
    @Length(max = 100, message = "Username must be between 4-20 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Item price cannot be null")
    @Min(0)
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull(message = "Item color cannot be null")
    @Column(name = "color", nullable = false)
    private Color color;

    @NotNull(message = "Item gender cannot be null")
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull(message = "Item size cannot be null")
    @Column(name = "size", nullable = false)
    private Size size;

    @NotNull(message = "Item SKU cannot be null")
    @Min(1)
    @Column(name = "SKU", nullable = false)
    private int sku;

    @NotNull(message = "Item category cannot be null")
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Cart> carts = new ArrayList<>();

    public void addImage(Image newImage) {
        this.images.add(newImage);
    }

    public void addToCart(Cart cart) {
        this.carts.add(cart);
    }
}
