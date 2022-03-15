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
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    public void addItem(Item newItem) {
        this.items.add(newItem);
    }

    public void removeItem(Item removableItem) {
        this.items.remove(removableItem);
    }
}
