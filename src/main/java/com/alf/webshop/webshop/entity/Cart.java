package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "total")
    private int total;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "carts")
    private List<Item> items;

    public void addItem(Item item) {
        this.items.add(item);
    }
}
