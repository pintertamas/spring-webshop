package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.exception.DiscountNotFoundException;
import com.alf.webshop.webshop.exception.NoDiscountAddedException;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
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
    public ResponseEntity<?> createDiscount(@Valid @RequestBody CreateDiscountRequest createDiscountRequest) {
        try {
            Discount newDiscount = discountService.createDiscount(createDiscountRequest);
            LogFactory.getLog(this.getClass()).info("DISCOUNT CREATED: " + newDiscount);
            return new ResponseEntity<>(newDiscount, HttpStatus.OK);
        } catch (DiscountAlreadyExistsException e) {
            LogFactory.getLog(this.getClass()).info("DISCOUNT ALREADY EXISTS: " + e.getExistingDiscount());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/add-discount")
    public ResponseEntity<?> addDiscountToListOfItems(@Valid @RequestBody DiscountRequest discountRequest) {
        try {
            discountService.addDiscountToItems(discountRequest);
            LogFactory.getLog(this.getClass()).info("DISCOUNT WITH THIS CODE: " + discountRequest.getDiscountCode() + " ADDED TO ITEMS WITH THESE IDS: " + discountRequest.getItemIds());
            return new ResponseEntity<>("Discount was successfully added to the items", HttpStatus.OK);
        } catch (DiscountNotFoundException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT FIND DISCOUNT WITH ID: " + discountRequest.getDiscountCode() + " " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoDiscountAddedException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT ADD DISCOUNT TO ANY ITEMS " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/remove-discount")
    public ResponseEntity<?> removeDiscountFromItems(@Valid @RequestBody DiscountRequest discountRequest) {
        try {
            discountService.removeDiscountFromItems(discountRequest);
            LogFactory.getLog(this.getClass()).info("DISCOUNT WITH THIS CODE: " + discountRequest.getDiscountCode() + " REMOVED FROM ITEMS WITH THESE IDS: " + discountRequest.getItemIds());
            return new ResponseEntity<>("Discount was successfully removed from the items", HttpStatus.OK);
        } catch (DiscountNotFoundException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT FIND DISCOUNT WITH ID: " + discountRequest.getDiscountCode() + " " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoDiscountAddedException e) {
            LogFactory.getLog(this.getClass()).info("COULD NOT ADD DISCOUNT TO ANY ITEMS " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
