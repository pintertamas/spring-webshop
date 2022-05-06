package com.alf.webshop.webshop.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import com.alf.webshop.webshop.WebshopApplication;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.juli.logging.LogFactory;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.RequestEntity.delete;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationContext.class)
//@WebMvcTest(DiscountController.class)
@RestClientTest(DiscountController.class)
public class DiscountControllerTest {
    Logger log = LoggerFactory.getLogger(CartControllerTest.class);
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private DiscountController discountController;

    @Mock
    private DiscountService discountService;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(discountController)
                .setControllerAdvice()
                .build();


    }
    @Test
    public void createDiscountTest()throws Exception{
        Discount d = new Discount();
        d.setId(1L);
        d.setCode("Y0VSU8");
        d.setDescription("discount desc");
        d.setDiscountPercent(5);
        d.setEndDate(new Date(2022-12-12));



        CreateDiscountRequest createDiscountRequest = new CreateDiscountRequest("y0vsu8","desc",5,new Date(2022-12-12));
        LogFactory.getLog("asd");
     //   doReturn(d).when(discountController).createDiscount(createDiscountRequest);
      //   Mockito.when(discountService.createDiscount(any(CreateDiscountRequest.class))).thenReturn(d);

        Mockito.when(discountService.createDiscount(any(CreateDiscountRequest.class))).thenReturn(d);

        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/discount/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(d)))
                .andReturn().getResponse();

        Discount discObj = objectMapper.readValue(response.getContentAsString(), Discount.class);

        assertEquals(d.getCode(), discObj.getCode());

        Mockito.verify(discountService).createDiscount(any(CreateDiscountRequest.class));


    }

    @Test
    public void addDiscountToListOfItemsTest()throws Exception{
        Item item  =new Item();
        item.setId(1L);
        item.setName("MockItem");
        item.setDescription("MockDesc");
        item.setPrice(12);
        item.setColor(Color.BLACK);
        item.setGender(Gender.MAN);
        item.setSize(Size._80E);
        item.setSku(2);
        item.setCategory(Category.BRA);
        //item.setDiscount();

        Discount discount = new Discount();
        discount.setDiscountPercent(10);
        discount.setCode("asd");
        discount.setDescription("asd");
        discount.setId(3L);
        discount.setEndDate(new Date(2022-10-10));

        item.setDiscount(discount);

        Item item2  =new Item();
        item.setId(2L);
        item.setName("MockItem2");
        item.setDescription("MockDesc2");
        item.setPrice(15432);
        item.setColor(Color.WHITE);
        item.setGender(Gender.WOMAN);
        item.setSize(Size._80E);
        item.setSku(2);
        item.setCategory(Category.BRA);


        List<Long> itemIds = new ArrayList<>();
        itemIds.add(1L);


        List<Category> categories = new ArrayList<>();
        categories.add(Category.BRALETTE);
        categories.add(Category.BRA);


        DiscountRequest dr= new DiscountRequest("desc",itemIds,categories,Gender.MAN);





        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/discount/add-discount",dr).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //assertTrue(response.getContentAsString().contains("DISCOUNT"));
        assertEquals("asd",response.getContentAsString());







    }





}
