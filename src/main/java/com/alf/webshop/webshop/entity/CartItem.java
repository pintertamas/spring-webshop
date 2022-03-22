package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "CartItem")
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "created_at")
    private Date createdAt;
}
