package com.alf.webshop.webshop.entity;

import javax.persistence.*;

@Entity(name = "Storage")
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    public int quantity;
}
