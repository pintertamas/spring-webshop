package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Image;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.Storage;
import com.alf.webshop.webshop.model.ItemRequest;
import com.alf.webshop.webshop.repository.ImageRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.repository.StorageRepository;
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
    StorageRepository storageRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@Valid @RequestBody ItemRequest _itemRequest) {
        Item item = new Item();
        item.setName(_itemRequest.getName());
        item.setColor(_itemRequest.getColor());
        item.setGender(_itemRequest.getGender());
        item.setDescription(_itemRequest.getDescription());
        item.setPrice(_itemRequest.getPrice());
        item.setImages(new ArrayList<>());
        Storage storage = new Storage();
        storage.setSize(_itemRequest.getStorage().getSize());
        storage.setQuantity(_itemRequest.getStorage().getQuantity());
        try {
            itemRepository.save(item);
            storage.setItem(item);
            storageRepository.save(storage);
            for (String imageUrl : _itemRequest.getImages()) {
                Image image = new Image();
                image.setUrl(imageUrl);
                imageRepository.save(image);
                item.addImage(image);
            }
            itemRepository.save(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
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