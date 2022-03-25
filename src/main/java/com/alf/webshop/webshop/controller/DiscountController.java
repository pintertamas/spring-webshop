package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.exception.DiscountNotFoundException;
import com.alf.webshop.webshop.exception.NoDiscountAddedException;
import com.alf.webshop.webshop.model.request.AddDiscountRequest;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.service.DiscountService;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/add-discount")
    public ResponseEntity<?> addDiscountToListOfItems(@Valid @RequestBody AddDiscountRequest addDiscountRequest) {
        try {
            discountService.addDiscountToItems(addDiscountRequest);
            LogFactory.getLog(this.getClass()).info("DISCOUNT WITH THIS CODE: " + addDiscountRequest.getDiscountCode() + " ADDED TO ITEMS WITH THESE IDS: " + addDiscountRequest.getItemIds());
            return new ResponseEntity<>("Discount was successfully added to the items", HttpStatus.OK);
        } catch (DiscountNotFoundException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT FIND DISCOUNT WITH ID: " + addDiscountRequest.getDiscountCode() + " " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoDiscountAddedException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT ADD DISCOUNT TO ANY ITEMS " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
