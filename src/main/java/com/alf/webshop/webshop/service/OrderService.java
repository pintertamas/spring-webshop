package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.NothingToOrderException;
import com.alf.webshop.webshop.model.response.OrderResponse;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.OrderDetailsRepository;
import com.alf.webshop.webshop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
//@RequiredArgsConstructor
public class OrderService {
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    CartRepository cartRepository;

    public List<OrderItem> createOrderFromCart() throws NothingToOrderException {
        User user = jwtUserDetailsService.getUserFromToken();
        Cart cart = user.getCart();
        if (user.getCart().getItems().size() == 0) throw new NothingToOrderException(user.getCart().getId());

        OrderDetails newOrderDetails = new OrderDetails();
        double total = 0;
        for (Item item : cart.getItems()) {
            if (item.getDiscount() == null) {
                total += item.getPrice();
            } else {
                total += item.getPrice() * (100 - item.getDiscount().getDiscountPercent()) / 100;
            }
        }
        newOrderDetails.setTotal(total);
        newOrderDetails.setUserId(user.getId());
        newOrderDetails.setCreatedAt(new Date(System.currentTimeMillis()));
        orderDetailsRepository.save(newOrderDetails);

        List<OrderItem> orderItems = new ArrayList();
        for (Item item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(newOrderDetails.getId());
            orderItem.setItemId(item.getId());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        user.getCart().setItems(new ArrayList<>());
        cartRepository.save(user.getCart());
        return orderItems;
    }

    public OrderResponse viewOrder(Long orderId) {
        OrderDetails orderDetails = orderDetailsRepository.findOrderDetailsById(orderId);
        User user = jwtUserDetailsService.getUserFromToken();
        if (!Objects.equals(orderDetails.getUserId(), user.getId()))
            throw new AuthorizationServiceException("You dont have permission to view this data");
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);
        return new OrderResponse(orderDetails, orderItems);
    }

    public List<OrderResponse> listOrders() {
        List<OrderResponse> orders = new ArrayList<>();
        String token = JwtTokenUtil.getToken();
        User user = jwtUserDetailsService.getUserFromToken();
        List<OrderDetails> orderDetails = orderDetailsRepository.findAllByUserId(user.getId());

        for (OrderDetails orderDetail : orderDetails) {
            List<OrderItem> items = orderItemRepository.findOrderItemsByOrderId(orderDetail.getId());
            orders.add(new OrderResponse(orderDetail, items));
        }
        return orders;
    }
}
