package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.JwtUserDetailsService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CartService.class)
@ContextConfiguration(classes = CartService.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService ;

    @InjectMocks
    private CartService cartService;

    @Before
    public void init(){
        cartRepository=Mockito.mock(CartRepository.class);
        itemRepository=Mockito.mock(ItemRepository.class);
        jwtUserDetailsService = Mockito.mock(JwtUserDetailsService.class);

        cartService = new CartService();
    }

    //happypath
    @Test
    public void addItemToCartTest( )throws Exception{
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

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(cartRepository.findCartById(1L)).thenReturn(cart);
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        //assertNotEquals(item,cartService.addItemToCart(1L));
        assertEquals(item,cartService.addItemToCart(1L));
    }

    //happypath
    @Test
    public void addItemToCartTestThrowsCartNotFoundException( )throws Exception{
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

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(cartRepository.findCartById(1L)).thenReturn(null);
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        //assertNotEquals(item,cartService.addItemToCart(1L));
        assertThrows(CartNotFoundException.class,()->{
            cartService.addItemToCart(1L);
        });
    }

    @Test
    public void addItemToCartTestThrowsItemNotFoundException( )throws Exception{
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

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(cartRepository.findCartById(1L)).thenReturn(cart);
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(null);
        //assertNotEquals(item,cartService.addItemToCart(1L));
        assertThrows(ItemNotFoundException.class,()->{
            cartService.addItemToCart(1L);
        });
    }

    //happypath
    @Test
    public void removeItemTest()throws Exception{
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

        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(cartRepository.findCartById(1L)).thenReturn(cart);
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        //assertNotEquals(item,cartService.addItemToCart(1L));
        Cart after = cartService.removeItem(1L);
        assertEquals(new ArrayList<Item>(),after.getItems());
    }

    @Test
    public void removeItemTestThrowsCartNotFoundException()throws Exception{
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

            ArrayList<Item> items = new ArrayList<>();
            items.add(item);
            cart.setItems(items);

            User user = new User();
            user.setUsername("peter");
            user.setCart(cart);

            Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
            Mockito.when(cartRepository.findCartById(1L)).thenReturn(null);
            Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);

            Mockito.when(cartRepository.findCartById(1L)).thenReturn(null);
            assertThrows(CartNotFoundException.class,()->{
                cartService.removeItem(1L);
            });
    }
    @Test
    public void removeItemTestThrowsItemNotFoundException()throws Exception{
        Cart cart = new Cart();
        cart.setId(10L);
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

        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        Mockito.when(cartRepository.findCartById(10L)).thenReturn(cart);
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(null);

        assertThrows(ItemNotFoundException.class,()->{
            cartService.removeItem(1L);
        });
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

        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        List<Item> returnitems = cartService.listCartItems();
        assertEquals(items,returnitems);

    }

    //exception
    @Test
    public void listCartItemsTestThrowsException()throws Exception{
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(0);


        User user = new User();
        user.setUsername("peter");
        user.setCart(cart);

        Mockito.when(jwtUserDetailsService.getUserFromToken()).thenReturn(user);
        assertThrows(EmptyListException.class,()->{
            cartService.listCartItems();
        });

    }
}
