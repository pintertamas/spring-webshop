package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "order_item")
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_id")
    private Long itemId;
}
