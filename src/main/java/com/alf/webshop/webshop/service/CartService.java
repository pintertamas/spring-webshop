package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    //@Autowired
    private CartRepository cartRepository;

    //@Autowired
    private ItemRepository itemRepository;

    //@Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public Item addItemToCart(Long id) throws Exception {
        User user = jwtUserDetailsService.getUserFromToken();
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        Item item = itemRepository.findItemById(id);

        if (cart == null) {
            throw new CartNotFoundException(user.getCart().getId());
        } else if (item == null) {
            throw new ItemNotFoundException(id);
        }

        try {
            cart.addItem(item);
            cartRepository.save(cart);
            item.addToCart(cart);
            cart.setTotal(cart.getTotal() + 1);
            itemRepository.save(item);
            return item;
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            throw new Exception();
        }
    }

    // deletes only one item matching the id given in the param from the users cart
    public Cart removeItem(Long id) throws Exception {
        User user = jwtUserDetailsService.getUserFromToken();
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        Item item = itemRepository.findItemById(id);

        if (cart == null) throw new CartNotFoundException(user.getCart().getId());
        if (item == null) throw new ItemNotFoundException(id);

        try {
            cart.getItems().remove(item);
            cartRepository.save(cart);
            return cart;
        } catch (Exception e) {
            throw new Exception("Could not remove item!");
        }
    }

    public List<Item> listCartItems() throws EmptyListException {
        User user = jwtUserDetailsService.getUserFromToken();
        List<Item> items = user.getCart().getItems();

        if (items.isEmpty()) {
            throw new EmptyListException();
        }
        return items;
    }
}
