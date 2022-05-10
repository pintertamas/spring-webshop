package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.exception.DiscountNotFoundException;
import com.alf.webshop.webshop.exception.NoDiscountAddedException;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.repository.DiscountRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    //@Autowired
    private DiscountRepository discountRepository;

    //@Autowired
    private ItemRepository itemRepository;

    public Discount createDiscount(CreateDiscountRequest createDiscountRequest) throws DiscountAlreadyExistsException {
        Discount discount = discountRepository.findDiscountByCode(createDiscountRequest.getCode());
        if (discount != null) throw new DiscountAlreadyExistsException(discount);
        discount = new Discount();
        discount.setCode(createDiscountRequest.getCode());
        discount.setDescription(createDiscountRequest.getDescription());
        discount.setDiscountPercent(createDiscountRequest.getDiscountPercent());
        discount.setEndDate(createDiscountRequest.getEndDate());
        discountRepository.save(discount);
        return discount;
    }

    //public because of testing
    public List<Item> saveItemsWithDiscount(List<Item> items, Discount discount) {
        List<Item> itemsResult = new ArrayList<>();
        items.forEach(item -> {
            item.setDiscount(discount);
            itemRepository.save(item);
            itemsResult.add(item);
        });
        return itemsResult;
    }

    private List<Item> removeDiscountFromItems(List<Item> items) {
        items.forEach(item -> {
            item.setDiscount(null);
            itemRepository.save(item);
        });
        return items;
    }

    private List<Item> editableItems(DiscountRequest discountRequest) throws DiscountNotFoundException, NoDiscountAddedException {
        Discount discount = discountRepository.findDiscountByCode(discountRequest.getDiscountCode());
        List<Item> editableItems = new ArrayList<>();
        if (discount == null) throw new DiscountNotFoundException(discountRequest.getDiscountCode());
        if (discountRequest.getItemIds() != null) {
            List<Item> items = itemRepository.findAllByIdIn(discountRequest.getItemIds());
            if (!items.isEmpty()) editableItems.addAll(items);
        }
        if (discountRequest.getCategories() != null) {
            List<Item> items;
            if (discountRequest.getGender() != null) {
                items = itemRepository.findAllByCategoryInAndGender(discountRequest.getCategories(), discountRequest.getGender());
            } else {
                items = itemRepository.findAllByCategoryIn(discountRequest.getCategories());
            }
            if (!items.isEmpty()) editableItems.addAll(items);
        }
        return editableItems;
    }

    public List<Item> addDiscountToItems(DiscountRequest discountRequest) throws DiscountNotFoundException, NoDiscountAddedException {
        Discount discount = discountRepository.findDiscountByCode(discountRequest.getDiscountCode());
        List<Item> editableItems = editableItems(discountRequest);
        return saveItemsWithDiscount(editableItems, discount);
    }

    public List<Item> removeDiscountFromItems(DiscountRequest discountRequest) throws DiscountNotFoundException, NoDiscountAddedException {
        List<Item> editableItems = editableItems(discountRequest);
        return removeDiscountFromItems(editableItems);
    }
}
