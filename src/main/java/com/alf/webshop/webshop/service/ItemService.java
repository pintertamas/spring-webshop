package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Image;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.Storage;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.CouldNotCreateInstanceException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.model.ItemRequest;
import com.alf.webshop.webshop.repository.ImageRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.repository.StorageRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Item createItem(ItemRequest itemRequest) throws CouldNotCreateInstanceException {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setColor(itemRequest.getColor());
        item.setGender(itemRequest.getGender());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setImages(new ArrayList<>());
        Storage storage = new Storage();
        storage.setSize(itemRequest.getStorage().getSize());
        storage.setQuantity(itemRequest.getStorage().getQuantity());
        try {
            itemRepository.save(item);
            storage.setItem(item);
            storageRepository.save(storage);
            for (String imageUrl : itemRequest.getImages()) {
                Image image = new Image();
                image.setUrl(imageUrl);
                imageRepository.save(image);
                item.addImage(image);
            }
            itemRepository.save(item);
            return item;
        } catch (Exception e) {
            throw new CouldNotCreateInstanceException();
        }
    }

    public List<Item> listItems() throws EmptyListException {
        List<Item> items = itemRepository.findAll();

        if (items.isEmpty()) {
            throw new EmptyListException();
        }
        return items;
    }

    public Item getItemById(Long id) throws ItemNotFoundException {
        Item item = itemRepository.findItemById(id);
        if (item == null) throw new ItemNotFoundException(id);
        return item;
    }

    public void deleteItem(IdRequest request) throws Exception {
        Item item = itemRepository.findItemById(request.getId());

        if (item == null) throw new ItemNotFoundException(request.getId());
        try {



            itemRepository.delete(item);
        } catch (Exception e) {
            throw new Exception("Something went wrong!");
        }
    }
}
