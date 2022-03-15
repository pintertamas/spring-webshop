package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.exception.CouldNotCreateInstanceException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.model.ItemRequest;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemRequest itemRequest) {
        try {
            Item newItem = itemService.createItem(itemRequest);
            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } catch (CouldNotCreateInstanceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllItems() {
        try {
            List<Item> items = itemService.listItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (EmptyListException ele) {
            return new ResponseEntity<>(ele.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<Item> getItemById(@RequestParam Long id) {
        try {
            Item item = itemService.getItemById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestBody IdRequest request) {
        try {
            itemService.deleteItem(request);
            return new ResponseEntity<>("ITEM DELETED", HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}