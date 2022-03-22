package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;
}
