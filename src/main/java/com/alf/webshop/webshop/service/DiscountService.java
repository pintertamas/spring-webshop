package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.exception.DiscountNotFoundException;
import com.alf.webshop.webshop.exception.NoDiscountAddedException;
import com.alf.webshop.webshop.model.request.AddDiscountRequest;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.repository.DiscountRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    ItemRepository itemRepository;

    public Discount createDiscount(DiscountRequest discountRequest) throws DiscountAlreadyExistsException {
        Discount discount = discountRepository.findDiscountByCode(discountRequest.getCode());
        if (discount != null) throw new DiscountAlreadyExistsException(discount);
        discount = new Discount();
        discount.setCode(discountRequest.getCode());
        discount.setDescription(discountRequest.getDescription());
        discount.setDiscountPercent(discountRequest.getDiscountPercent());
        discount.setEndDate(discountRequest.getEndDate());
        discountRepository.save(discount);
        return discount;
    }

    private void saveItemsWithDiscount(List<Item> items, Discount discount) {
        items.forEach(item -> {
            item.setDiscount(discount);
            itemRepository.save(item);
        });
    }

    public void addDiscountToItems(AddDiscountRequest addDiscountRequest) throws DiscountNotFoundException, NoDiscountAddedException {
        Discount discount = discountRepository.findDiscountByCode(addDiscountRequest.getDiscountCode());
        List<Item> editedItems = new ArrayList<>();
        if (discount == null) throw new DiscountNotFoundException(addDiscountRequest.getDiscountCode());
        if (addDiscountRequest.getItemIds() != null) {
            List<Item> items = itemRepository.findAllByIdIn(addDiscountRequest.getItemIds());
            if (!items.isEmpty()) editedItems.addAll(items);
        }
        if (addDiscountRequest.getCategories() != null) {
            List<Item> items;
            if (addDiscountRequest.getGender() != null) {
                items = itemRepository.findAllByCategoryInAndGender(addDiscountRequest.getCategories(), addDiscountRequest.getGender());
            } else {
                items = itemRepository.findAllByCategoryIn(addDiscountRequest.getCategories());
            }
            if (!items.isEmpty()) editedItems.addAll(items);
        }
        if (!editedItems.isEmpty()) saveItemsWithDiscount(editedItems, discount);
        else throw new NoDiscountAddedException();
    }
}
