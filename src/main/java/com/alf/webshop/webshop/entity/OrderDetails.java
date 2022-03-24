package com.alf.webshop.webshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
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
    private Long total;

    @Column(name = "created_at")
    private Date createdAt;
}
