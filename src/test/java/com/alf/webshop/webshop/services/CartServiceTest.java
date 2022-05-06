package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.DiscountService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.lang.Long;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CartService.class)
@ContextConfiguration(classes = CartService.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository=Mockito.mock(CartRepository.class);
    @Mock
    private ItemRepository itemRepository=Mockito.mock(ItemRepository.class);
    @Autowired
    private final CartService cartService=Mockito.mock(CartService.class);



    @Test
    public void addItemToCart( )throws Exception{
        CartService c = mock(CartService.class);

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

        //Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        //Mockito.when(cartRepository.findCartById(1L)).thenReturn(cart);
        //assertEquals(1,cartService.addItemToCart());
        //cartService.addItemToCart(null);



    }

}
