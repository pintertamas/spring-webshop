package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemByItemId(Long itemId);

    List<CartItem> findCartItemByCartId(Long cartId);

    void deleteAllByItemId(Long itemId);

    CartItem findFirstByItemIdAndCartId(Long itemId, Long cartId);
}
