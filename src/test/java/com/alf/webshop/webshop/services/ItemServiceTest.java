package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.entity.Category;
import com.alf.webshop.webshop.entity.Gender;
import com.alf.webshop.webshop.entity.Item;
import com.alf.webshop.webshop.entity.Size;
import com.alf.webshop.webshop.exception.ItemNotFoundException;
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
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



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

    @Test
    public void saveItemTest()throws Exception{
        ArrayList<String> images = new ArrayList<>();
        String image = "first image";
        String image2 = "second image";
        images.add(image);
        images.add(image2);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("gatya");
        itemRequest.setDescription("ez egy gatya");
        itemRequest.setPrice(1335);
        itemRequest.setColor(Color.WHITE);
        itemRequest.setGender(Gender.MAN);
        itemRequest.setCategory(Category.BOXERS);
        itemRequest.setImages(images);
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
        item.setSize(Size._90E);
        item.setSku(3);
        //Item after = itemService.saveItem(item,itemRequest);
        //assertEquals(item.getColor(),after.getColor());
        //assertEquals(item.getDescription(),after.getDescription());
        //assertEquals(item.getPrice(),after.getPrice());
    }

    @Test
    public void createItemTest()throws Exception{
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

        Item zero = new Item();

        Item item = new Item();
        item.setId(1L);
        item.setName("gatya");
        item.setDescription("ez egy gatya");
        item.setPrice(1335);
        item.setColor(Color.WHITE);
        item.setGender(Gender.MAN);
        item.setCategory(Category.BOXERS);
        item.setSize(Size._90E);
        item.setSku(3);

        Item after = itemService.createItem(itemRequest);
        assertEquals(item.getPrice(),after.getPrice());
        assertEquals(item.getName(),after.getName());
        assertEquals(item.getColor(),after.getColor());
        assertEquals(item.getDescription(),after.getDescription());
        assertEquals(item.getPrice(),after.getPrice());

    }
    @Test
    public void editItemTest()throws Exception{
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

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest.setName("gatya");
        itemRequest.setDescription("ez egy gatya");
        itemRequest.setPrice(1335);
        itemRequest.setColor(Color.BLACK);
        itemRequest.setGender(Gender.WOMAN);
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
        item.setSize(Size._90E);
        item.setSku(3);

        Item item2 = new Item();
        item.setId(1L);
        item.setName("gatya");
        item.setDescription("ez egy gatya");
        item.setPrice(1335);
        item.setColor(Color.BLACK);
        item.setGender(Gender.WOMAN);
        item.setCategory(Category.BOXERS);
        item.setSize(Size._90E);
        item.setSku(3);


        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        Item after = itemService.editItem(1L,itemRequest2);

        assertEquals(Gender.WOMAN,after.getGender());
        assertEquals(Color.BLACK,after.getColor());



    }

    @Test
    public void editItemTestThrowsItemNotFoundException()throws Exception{
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

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest.setName("gatya");
        itemRequest.setDescription("ez egy gatya");
        itemRequest.setPrice(1335);
        itemRequest.setColor(Color.BLACK);
        itemRequest.setGender(Gender.WOMAN);
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
        item.setSize(Size._90E);
        item.setSku(3);

        Item item2 = new Item();
        item.setId(1L);
        item.setName("gatya");
        item.setDescription("ez egy gatya");
        item.setPrice(1335);
        item.setColor(Color.BLACK);
        item.setGender(Gender.WOMAN);
        item.setCategory(Category.BOXERS);
        item.setSize(Size._90E);
        item.setSku(3);


        Mockito.when(itemRepository.findItemById(1L)).thenReturn(null);

        assertThrows(ItemNotFoundException.class,()->{
            itemService.editItem(item.getId(),itemRequest2);
        });



    }

    @Test
    public void listItems()throws Exception{
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



        Mockito.when(itemRepository.findAll()).thenReturn(items);
        List<Item> returnitems = itemService.listItems();
        assertEquals(items,returnitems);

    }
    @Test
    public void listItemsThrowsEmptyListException()throws Exception{
        List<Item> items = new ArrayList<>();

        Mockito.when(itemRepository.findAll()).thenReturn(items);
        assertThrows(EmptyListException.class,()->{
            itemService.listItems();
        });
    }
    @Test
    public void getItemByIdTest() throws Exception {
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

        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        assertEquals(item,itemService.getItemById(1L));

    }
    @Test
    public void getItemByIdTestThrowsItemNotFoundException() throws Exception {
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

        Mockito.when(itemRepository.findItemById(1L)).thenReturn(null);
        assertThrows(ItemNotFoundException.class,()->{
            itemService.getItemById(1L);
        });

    }
    @Test
    public void deleteItemThrowsItemNotFoundException()throws Exception{
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(null);
        assertThrows(ItemNotFoundException.class,()->{
            itemService.deleteItem(1L);
        });
    }

    @Test
    public void deleteItem()throws Exception{
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
        List<Item> items=new ArrayList<Item>();
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        Cart cart2 = new Cart();
        cart2.setItems(items);

        Mockito.when(itemRepository.findItemById(1L)).thenReturn(item);
        itemService.deleteItem(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).delete(item);

    }


}
