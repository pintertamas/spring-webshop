package com.alf.webshop.webshop.model.response;

import com.alf.webshop.webshop.entity.OrderDetails;
import com.alf.webshop.webshop.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponse {
    OrderDetails orderDetails;
    List<OrderItem> orderItems;
}
