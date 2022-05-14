package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.entity.Category;
import com.alf.webshop.webshop.entity.Gender;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.Size;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ImageRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.service.ItemService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;



        import com.alf.webshop.webshop.entity.*;
        import com.alf.webshop.webshop.exception.EmptyListException;
        import com.alf.webshop.webshop.model.request.ItemRequest;
        import com.alf.webshop.webshop.repository.CartRepository;
        import com.alf.webshop.webshop.repository.ImageRepository;
        import com.alf.webshop.webshop.repository.ItemRepository;
        import com.alf.webshop.webshop.service.CartService;
        import com.alf.webshop.webshop.service.ItemService;
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
        import org.springframework.test.context.junit4.SpringRunner;
        import java.util.ArrayList;
        import java.util.List;
        import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ItemService.class)
@ContextConfiguration(classes = ItemService.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ItemService itemService;

    @Before
    public void init(){
        cartRepository=Mockito.mock(CartRepository.class);
        itemRepository=Mockito.mock(ItemRepository.class);
        imageRepository=Mockito.mock(ImageRepository.class);
        itemService = new ItemService();
    }

    //happypath
    @Test
    public void itemFromRequestTest( )throws Exception{
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("gatya");
        itemRequest.setDescription("ez egy gatya");
        itemRequest.setPrice(1335);
        itemRequest.setColor(Color.WHITE);
        itemRequest.setGender(Gender.MAN);
        itemRequest.setCategory(Category.BOXERS);
        itemRequest.setImages(new ArrayList<String>());
        itemRequest.setSize(Size._90E);
        itemRequest.setSku(3);

        Item item = new Item();
        item.setId(1L);
        item.setName("gatya");
        item.setDescription("ez egy gatya");
        item.setPrice(1335);
        item.setColor(Color.WHITE);
        item.setGender(Gender.MAN);
        item.setCategory(Category.BOXERS);
//        item.setImages(new ArrayList<String>());
        item.setSize(Size._90E);
        item.setSku(3);

        Mockito.when(itemRepository.findItemById(2L)).thenReturn(item);
        assertEquals(item,itemRepository.findItemById(2L));

        Item item_test = itemService.createItem(itemRequest);
        //assertEquals(item.getName(),item_test.getName());

    }

}
