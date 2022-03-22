package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.CartItem;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.repository.CartItemRepository;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Item addItemToCart(IdRequest request) throws Exception {
        String token = JwtTokenUtil.getToken();
        User user = jwtTokenUtil.getUserFromToken(token);
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        Item item = itemRepository.findItemById(request.getId());

        if (cart == null) {
            throw new CartNotFoundException(user.getCart().getId());
        } else if (item == null) {
            throw new ItemNotFoundException(request.getId());
        }

        try {
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setItemId(item.getId());
            cartItemRepository.save(cartItem);

            return item;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // deletes only one item matching the id given in the param from the users cart
    public void removeItem(IdRequest request) throws Exception {
        String token = JwtTokenUtil.getToken();
        User user = jwtTokenUtil.getUserFromToken(token);
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        Item item = itemRepository.findItemById(request.getId());

        if (cart == null) throw new CartNotFoundException(user.getCart().getId());
        if (item == null) throw new ItemNotFoundException(request.getId());

        try {
            CartItem removableCartItem = cartItemRepository.findFirstByItemIdAndCartId(item.getId(), cart.getId());
            cartItemRepository.delete(removableCartItem);

            // TODO: probably should not write db on every item deletion
        } catch (Exception e) {
            throw new Exception("Could not remove item!");
        }
    }

    public List<Item> listCartItems() throws EmptyListException {
        String token = JwtTokenUtil.getToken();
        User user = jwtTokenUtil.getUserFromToken(token);
        List<CartItem> cartItems = cartItemRepository.findCartItemByCartId(user.getCart().getId());

        List<Item> items = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Item item = itemRepository.findItemById(cartItem.getItemId());
            items.add(item);
        }


        if (items.isEmpty()) {
            throw new EmptyListException();
        }
        return items;
    }
}
