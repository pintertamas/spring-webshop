package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}
