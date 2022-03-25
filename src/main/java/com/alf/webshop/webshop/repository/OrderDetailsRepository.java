package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByUserId(Long userId);
    OrderDetails findOrderDetailsById(Long orderId);
}
