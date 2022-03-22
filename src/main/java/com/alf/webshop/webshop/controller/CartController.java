package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.model.request.IdRequest;
import com.alf.webshop.webshop.model.response.ItemResponse;
import com.alf.webshop.webshop.service.CartService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody IdRequest request) {
        try {
            cartService.addItemToCart(request);
            return new ResponseEntity<>("Item with ID: " + request.getId() + " was successfully added to your cart!", HttpStatus.OK);
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
            LogFactory.getLog(this.getClass()).info("ITEM WITH ID: " + request.getId() + " DELETED FROM CART");
            return new ResponseEntity<>("Item with id: " + request.getId() + " deleted from cart", HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCartItems() {
        try {
            List<Item> items = cartService.listCartItems();
            LogFactory.getLog(this.getClass()).info("ITEMS LISTED");
            return new ResponseEntity<>(ItemResponse.fromItemList(items), HttpStatus.OK);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
