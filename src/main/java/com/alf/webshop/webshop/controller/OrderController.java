package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.exception.NothingToOrderException;
import com.alf.webshop.webshop.model.response.OrderResponse;
import com.alf.webshop.webshop.service.OrderService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder() {
        try {
            orderService.orderItemsInCart();
            LogFactory.getLog(this.getClass()).info("ORDER CREATED");
            return new ResponseEntity<>("Your order was successful!", HttpStatus.OK);
        } catch (NothingToOrderException e) {
            LogFactory.getLog(this.getClass()).info("NOTHING TO ORDER");
            return new ResponseEntity<>("Your cart is empty!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewOrder(@PathVariable Long id) {
        try {
            OrderResponse order = orderService.viewOrder(id);
            LogFactory.getLog(this.getClass()).info("ORDER LISTED");
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).info("ORDER VIEW ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listOrders() {
        try {
            List<OrderResponse> orders = orderService.listOrders();
            LogFactory.getLog(this.getClass()).info("ORDERS LISTED");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).info("ORDER LIST ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
