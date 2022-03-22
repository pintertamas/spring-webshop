package com.alf.webshop.webshop.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "total")
    private int total;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        this.items.add(item);
    }
}
