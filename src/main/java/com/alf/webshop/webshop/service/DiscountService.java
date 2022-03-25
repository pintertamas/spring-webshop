package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    DiscountRepository discountRepository;

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
}
