package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.service.CartService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody IdRequest request) {
        try {
            Item item = cartService.addItemToCart(request);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteItemFromCart(@Valid @RequestBody IdRequest request) {
        try {
            // removes one item from the current user's cart ba the ITEM ID!
            cartService.removeItem(request);
            LogFactory.getLog(this.getClass()).error("ITEM WITH ID: " + request.getId() + " DELETED");
            return new ResponseEntity<>("Item with id: " + request.getId() + " deleted", HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
