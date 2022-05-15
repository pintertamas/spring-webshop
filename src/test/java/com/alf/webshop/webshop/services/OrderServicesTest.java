package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.NothingToOrderException;
import com.alf.webshop.webshop.model.response.OrderResponse;
import com.alf.webshop.webshop.repository.*;
import com.alf.webshop.webshop.service.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(OrderService.class)
@ContextConfiguration(classes = OrderService.class)
public class OrderServicesTest {

    @InjectMocks
    OrderService orderService ;

    @Mock
    OrderItemRepository orderItemRepository = Mockito.mock(OrderItemRepository.class);

    @Mock
    OrderDetailsRepository orderDetailsRepository = Mockito.mock(OrderDetailsRepository.class);

    @Mock
    CartRepository cartRepository = Mockito.mock(CartRepository.class);

    @Mock
    JwtUserDetailsService jwtUserDetailsService = Mockito.mock(JwtUserDetailsService.class);



    @Before // MockitoAnnotations.openMocks(this);
    public void init(){
        orderService = new OrderService();
    }

    //HappyPath
    @Test
    public void createOrderFromCartTest( )throws Exception{
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setId(1L);
        user.setCart(cart);
        user.setUsername("peter");
        user.setPassword("pass");
        user.setCart(cart);

        OrderDetails newOrederDetails = new OrderDetails();
        newOrederDetails.setTotal(12);
        newOrederDetails.setUserId(1L);
        newOrederDetails.setId(2L);
        newOrederDetails.setCreatedAt(new Date(System.currentTimeMillis()));

        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        OrderItem orderItem =new OrderItem();
        orderItem.setOrderId(newOrederDetails.getId());
        orderItem.setItemId(1L);
        orderItemArrayList.add(orderItem);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);

        assertEquals(1,orderService.createOrderFromCart().size());


    }
    @Test
    public void createOrderFromCartTestThrowsNothingToOrderException( )throws Exception{
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(0);
        ArrayList<Item> items = new ArrayList<>();
        cart.setItems(items);

        User user = new User();
        user.setId(1L);
        user.setCart(cart);
        user.setUsername("peter");
        user.setPassword("pass");
        user.setCart(cart);


        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        assertThrows(NothingToOrderException.class,()->{
            orderService.createOrderFromCart();
        });

    }

    @Test
    public void viewOrder(){
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setId(1L);
        user.setCart(cart);
        user.setUsername("peter");
        user.setPassword("pass");
        user.setCart(cart);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setTotal(12);
        orderDetails.setUserId(1L);
        orderDetails.setId(2L);
        orderDetails.setCreatedAt(new Date(System.currentTimeMillis()));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =new OrderItem();
        orderItem.setOrderId(orderDetails.getId());
        orderItem.setItemId(1L);
        orderItemList.add(orderItem);

        Mockito.when(orderDetailsRepository.findOrderDetailsById(2L)).thenReturn(orderDetails);
        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(orderItemRepository.findOrderItemsByOrderId(2L)).thenReturn(orderItemList);

        OrderResponse orderResponse=new OrderResponse(orderDetails,orderItemList);
        OrderResponse afterOrderResponse=orderService.viewOrder(2L);
        assertEquals(orderResponse.getOrderDetails(),afterOrderResponse.getOrderDetails());

    }

    @Test
    public void viewOrderThrowsAuthorizationServiceException()throws Exception{
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

        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setId(1L);
        user.setCart(cart);
        user.setUsername("peter");
        user.setPassword("pass");
        user.setCart(cart);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setTotal(12);
        orderDetails.setUserId(12312L);
        orderDetails.setId(2L);
        orderDetails.setCreatedAt(new Date(System.currentTimeMillis()));




        Mockito.when(orderDetailsRepository.findOrderDetailsById(2L)).thenReturn(orderDetails);
        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        assertThrows(AuthorizationServiceException.class,()->{
            orderService.viewOrder(2L);
        });
    }



}
