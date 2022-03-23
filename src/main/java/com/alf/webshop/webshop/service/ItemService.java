package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Image;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.exception.CouldNotCreateInstanceException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Item itemFromRequest(Item item, ItemRequest itemRequest) {
        if (itemRequest.getName() != null) item.setName(itemRequest.getName());
        if (itemRequest.getColor() != null) item.setColor(itemRequest.getColor());
        if (itemRequest.getGender() != null) item.setGender(itemRequest.getGender());
        if (itemRequest.getDescription() != null) item.setDescription(itemRequest.getDescription());
        if (itemRequest.getPrice() >= 0) item.setPrice(itemRequest.getPrice());
        if (itemRequest.getSize() != null) item.setSize(itemRequest.getSize());
        if (itemRequest.getSku() >= 0) item.setSku(itemRequest.getSku());
        if (itemRequest.getCategory() != null) item.setCategory(itemRequest.getCategory());
        if (item.getImages() == null) item.setImages(new ArrayList<>());

        return item;
    }

    //saves an item to the database
    private Item saveItem(Item item, ItemRequest itemRequest) throws CouldNotCreateInstanceException {
        itemRepository.save(item);

        try {
            if (itemRequest.getImages() != null && itemRequest.getImages().size() > 0) {
                for (String imageUrl : itemRequest.getImages()) {
                    Image image = new Image();
                    image.setUrl(imageUrl);
                    imageRepository.save(image);
                    item.addImage(image);
                }
                itemRepository.save(item);
            }
            return item;
        } catch (Exception e) {
            throw new CouldNotCreateInstanceException();
        }
    }

    public Item createItem(ItemRequest itemRequest) throws CouldNotCreateInstanceException {
        Item item = itemFromRequest(new Item(), itemRequest);
        return saveItem(item, itemRequest);
    }

    public Item editItem(Long itemId, ItemRequest editedItem) throws ItemNotFoundException, CouldNotCreateInstanceException {
        Item item = itemRepository.findItemById(itemId);
        if (item == null) throw new ItemNotFoundException(itemId);

        itemFromRequest(item, editedItem);
        item.setId(itemId);
        return saveItem(item, editedItem);
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

    @Transactional
    public void deleteItem(Long id) throws Exception {
        Item item = itemRepository.findItemById(id);

        if (item == null) throw new ItemNotFoundException(id);
        try {
            List<Cart> carts = item.getCarts();
            for (Cart cart : carts) {
                cart.getItems().remove(item);
                cartRepository.save(cart);
            }
            itemRepository.delete(item);
        } catch (Exception e) {
            throw new Exception("Something went wrong! " + e.getMessage());
        }
    }
}
