package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.service.DiscountService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @PostMapping("/create")
    public ResponseEntity<?> createDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        try {
            Discount newDiscount = discountService.createDiscount(discountRequest);
            LogFactory.getLog(this.getClass()).info("DISCOUNT CREATED: " + newDiscount);
            return new ResponseEntity<>(newDiscount, HttpStatus.OK);
        } catch (DiscountAlreadyExistsException e) {
            LogFactory.getLog(this.getClass()).info("DISCOUNT ALREADY EXISTS: " + e.getExistingDiscount());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
