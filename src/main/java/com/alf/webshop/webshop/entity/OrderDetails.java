package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "order_detail")
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total")
    private double total;

    @Column(name = "created_at")
    private Date createdAt;
}
