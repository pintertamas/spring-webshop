package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findCartById(Long id);
}
