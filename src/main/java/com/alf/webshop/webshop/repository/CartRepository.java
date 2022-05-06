package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findCartById(Long id);
}
