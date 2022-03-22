package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Item")
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "color", nullable = false)
    private Color color;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "size", nullable = false)
    private Size size;

    @Column(name = "SKU", nullable = false)
    private int sku;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private List<Cart> carts;

    public void addImage(Image newImage) {
        this.images.add(newImage);
    }

    public void addToCart(Cart cart) {
        this.carts.add(cart);
    }
}
