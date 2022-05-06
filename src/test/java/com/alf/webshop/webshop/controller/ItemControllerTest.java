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
import com.alf.webshop.webshop.model.response.ItemResponse;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.DiscountService;
import com.alf.webshop.webshop.service.ItemService;
import com.fasterxml.jackson.core.JsonParser;
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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    Logger log = LoggerFactory.getLogger(CartControllerTest.class);
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,true);

        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice()
                .build();


    }
    @Test
    public void createItemTest()throws Exception{
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

        ItemResponse ir = new ItemResponse(item);

        Mockito.when(itemService.createItem(any((ItemRequest.class)))).thenReturn(item);

        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/item/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ir)))
                .andReturn().getResponse();

        Item itemObj = objectMapper.readValue(response.getContentAsString(), Item.class);

        assertEquals(item.getColor(), itemObj.getColor());
        assertEquals(Color.GREEN, itemObj.getColor());


        Mockito.verify(itemService).createItem(any(ItemRequest.class));


    }








}
