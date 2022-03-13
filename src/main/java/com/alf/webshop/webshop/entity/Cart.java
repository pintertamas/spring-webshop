package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Item> items;

    @Column(name = "quantity")
    public int quantity;

    public Cart(User _user) {
        this.user = _user;
        this.items = new ArrayList<>();
    }
}
