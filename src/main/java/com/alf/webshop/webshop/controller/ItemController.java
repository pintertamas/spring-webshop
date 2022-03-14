package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Image;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.model.ItemRequest;
import com.alf.webshop.webshop.repository.ImageRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import org.apache.juli.logging.LogFactory;
import org.hibernate.TransientPropertyValueException;
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
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemRequest _itemRequest) {
        Item _item = new Item();
        String token = JwtTokenUtil.getToken();
        _item.setCreator(jwtTokenUtil.getUserFromToken(token));
        _item.setName(_itemRequest.getName());
        _item.setDescription(_itemRequest.getDescription());
        _item.setPrice(_itemRequest.getPrice());
        _item.setImages(new ArrayList<>());
        try {
            itemRepository.save(_item);
            for (String imageUrl : _itemRequest.getImages()) {
                Image image = new Image();
                image.setUrl(imageUrl);
                imageRepository.save(image);
                _item.addImage(image);
            }
            itemRepository.save(_item);
            return new ResponseEntity<>(_item, HttpStatus.OK);
        } catch (TransientPropertyValueException e) {
            LogFactory.getLog(this.getClass()).error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = new ArrayList<>(itemRepository.findAll());

            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Item> getItemById(@RequestParam String id) {
        Optional<Item> itemsData = itemRepository.findById(id);
        return itemsData.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}