package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.exception.CouldNotCreateInstanceException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.model.request.IdRequest;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.model.response.ItemResponse;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.ItemService;
import org.apache.commons.logging.LogFactory;
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
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemRequest itemRequest) {
        try {
            Item newItem = itemService.createItem(itemRequest);
            LogFactory.getLog(this.getClass()).info("ITEM CREATED: " + itemRequest);
            return new ResponseEntity<>(new ItemResponse(newItem), HttpStatus.OK);
        } catch (CouldNotCreateInstanceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllItems() {
        try {
            List<Item> items = itemService.listItems();
            LogFactory.getLog(this.getClass()).info(items.size() + " ITEMS LISTED");
            return new ResponseEntity<>(ItemResponse.fromItemList(items), HttpStatus.OK);
        } catch (EmptyListException ele) {
            return new ResponseEntity<>(ele.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            Item item = itemService.getItemById(id);
            LogFactory.getLog(this.getClass()).info("ITEM BY ID: " + item);
            return new ResponseEntity<>(new ItemResponse(item), HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editItemById(@PathVariable Long id, @Valid @RequestBody ItemRequest itemRequest) {
        try {
            Item item = itemService.editItem(id, itemRequest);
            LogFactory.getLog(this.getClass()).info("ITEM BY ID: " + item);
            return new ResponseEntity<>(new ItemResponse(item), HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            LogFactory.getLog(this.getClass()).error("COULD NOT FIND ITEM BY ID: " + id);
            return new ResponseEntity<>("Could not find item with id: " + id, HttpStatus.NOT_FOUND);
        } catch (CouldNotCreateInstanceException e) {
            LogFactory.getLog(this.getClass()).error("COULD NOT EDIT ITEM WITH ID: " + id);
            return new ResponseEntity<>(itemRequest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            LogFactory.getLog(this.getClass()).info("ITEM WITH ID: " + id + " DELETED");
            return new ResponseEntity<>("Item with ID: " + id + " deleted", HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}