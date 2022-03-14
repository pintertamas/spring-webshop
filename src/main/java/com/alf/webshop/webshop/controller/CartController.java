package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody IdRequest request) {
        String token = JwtTokenUtil.getToken();
        User user = jwtTokenUtil.getUserFromToken(token);
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        Item item = itemRepository.findItemById(request.getId());

        if (cart == null || item == null) {
            LogFactory.getLog(this.getClass()).error("CART OR ITEM COULD NOT BE FOUND!");
            return new ResponseEntity<>("Cart or item could not be found!", HttpStatus.NOT_FOUND);
        }

        try {
            cart.addItem(item);
            cartRepository.save(cart);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
