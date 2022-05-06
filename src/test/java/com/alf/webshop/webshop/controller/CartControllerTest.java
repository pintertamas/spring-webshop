package com.alf.webshop.webshop.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.RequestEntity.delete;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(CartService.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class CartControllerTest {
    Logger log = LoggerFactory.getLogger(CartControllerTest.class);
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
                .setControllerAdvice()
                .build();
    }


    @Test
    public void listCartItemsTest()throws Exception{
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(0);


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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        cart.setItems(items);

        //Mockito.when(cartController.listCartItems()).then);
        doReturn(items).when(cartService).listCartItems();

        //this.mockMvc.perform(get("/list").andExpect(status().isOk()))
        this.mockMvc.perform(get("/cart/list")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) // check the size of post array in response
//                .andExpect(jsonPath("$[0].content").value("Content")).andExpect(jsonPath("$[0].title").value("Title"))
//                .andExpect(jsonPath("$[0].createdOn").value(IsNull.notNullValue()))
//                .andExpect(jsonPath("$[0].updatedOn").value(IsNull.nullValue())).andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].comments.*", hasSize(2))) // check the size of comments array in response
//                .andExpect(jsonPath("$[0].comments[0].id", is(1)))
//                .andExpect(jsonPath("$[0].comments[0].content", is("This is comment")))
//                .andExpect(jsonPath("$[1].content").value("Content2")).andExpect(jsonPath("$[1].title").value("Title2"))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].comments", is(IsEmptyString.emptyOrNullString()))) // check for empty or null  value
//                .andExpect(jsonPath("$[1].comments", is(IsNull.nullValue()))) // check for null value
//                .andExpect(jsonPath("$[0:].content", Matchers.containsInAnyOrder("Content2", "Content")))
//                .andExpect(jsonPath("$[0:].content", Matchers.containsInRelativeOrder("Content", "Content2")));
        ;

    }
    @Test
    public void deleteItemFromCartTest () throws Exception{
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(0);


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

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        cart.setItems(items);

        //Mockito.when(cartController.listCartItems()).then);
        //doReturn(items).when(cartService).removeItem(1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cart/remove/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
                       // .andExpect(jsonPath("$.length()", is(1)));


    }
    @Test
    public void addItemToCartTest() throws Exception{
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(0);


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

        List<Item> items = new ArrayList<>();
        items.add(item);

        cart.setItems(items);
        //TODO

    }




}
