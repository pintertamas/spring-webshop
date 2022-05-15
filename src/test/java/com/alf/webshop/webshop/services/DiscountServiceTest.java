package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.model.request.DiscountRequest;
import com.alf.webshop.webshop.repository.*;
import com.alf.webshop.webshop.service.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.Long;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DiscountService.class)
@ContextConfiguration(classes = DiscountService.class)
public class DiscountServiceTest {

   @Mock
   DiscountRepository discountRepository;

   @Mock
   ItemRepository itemRepository;

    @InjectMocks
    DiscountService discountService ;




    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        discountService = new DiscountService();
    }

    @Test
    public void createDiscountTestThrowsDiscountAlreadyExistsException()throws Exception{
        CreateDiscountRequest createDiscountRequest = new CreateDiscountRequest("y0vsu8","desc",5,new Date(2022-02-02));

        Discount discount_final=new Discount();
        discount_final.setId(1L);
        discount_final.setCode("y0vsu8");
        discount_final.setDescription("desc");
        discount_final.setDiscountPercent(5);
        discount_final.setEndDate(new Date(2022-02-02));

        Mockito.when(discountRepository.findDiscountByCode(createDiscountRequest.getCode())).thenReturn(discount_final);
        assertThrows(DiscountAlreadyExistsException.class,()->{
            discountService.createDiscount(createDiscountRequest);
        });
    }

    @Test
    public void createDiscountTest()throws Exception{
        CreateDiscountRequest createDiscountRequest = new CreateDiscountRequest("y0vsu8","desc",5,new Date(2022-02-02));

        Discount discount_final=new Discount();
        discount_final.setId(1L);
        discount_final.setCode("y0vsu8");
        discount_final.setDescription("desc");
        discount_final.setDiscountPercent(5);
        discount_final.setEndDate(new Date(2022-02-02));

        Mockito.when(discountRepository.findDiscountByCode(createDiscountRequest.getCode())).thenReturn(null);
        Discount d = discountService.createDiscount(createDiscountRequest);
        assertEquals(discount_final.getDiscountPercent(),d.getDiscountPercent());
        assertEquals(discount_final.getEndDate(),d.getEndDate());
        assertEquals(discount_final.getCode(),d.getCode());
    }

    @Test
    public void saveItemsWithDiscountTest(){
        Discount discount=new Discount();
        discount.setId(1L);
        discount.setCode("y0vsu8");
        discount.setDescription("desc");
        discount.setDiscountPercent(5);
        discount.setEndDate(new Date(2022-02-02));

        List<Item> items = new ArrayList<>();
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
        items.add(item);


        List<Item> after = discountService.saveItemsWithDiscount(items,discount);
        item.setDiscount(discount);
        assertEquals(items,after);
    }
    @Test
    public void removeItemsWithDiscountTest(){
        Discount discount=new Discount();
        discount.setId(1L);
        discount.setCode("y0vsu8");
        discount.setDescription("desc");
        discount.setDiscountPercent(5);
        discount.setEndDate(new Date(2022-02-02));

        List<Item> items = new ArrayList<>();
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
        item.setDiscount(discount);
        items.add(item);

        List<Item> after = discountService.removeDiscountFromItems(items);
        item.setDiscount(null);
        assertEquals(items,after);
    }
    @Test
    public void editableItemsTest(){
        List<Long> ids= new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        List<Category> categories = new ArrayList<>();
        categories.add(Category.BRALETTE);
        DiscountRequest discountRequest = new DiscountRequest("Y0VSU8",ids,categories,Gender.WOMAN);

        Discount discount=new Discount();
        discount.setId(1L);
        discount.setCode("y0vsu8");
        discount.setDescription("desc");
        discount.setDiscountPercent(5);
        discount.setEndDate(new Date(2022-02-02));
        Mockito.when(discountRepository.findDiscountByCode(discountRequest.getDiscountCode())).thenReturn(discount);
    }

    public void addDiscountToItemsTest()throws Exception{
        List<Long> ids= new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        List<Category> categories = new ArrayList<>();
        categories.add(Category.BRALETTE);
        DiscountRequest discountRequest = new DiscountRequest("Y0VSU8",ids,categories,Gender.WOMAN);
        Discount discount=new Discount();
        discount.setId(1L);
        discount.setCode("y0vsu8");
        discount.setDescription("desc");
        //discount.setDiscountPercent(5);
        discount.setEndDate(new Date(2022-02-02));

        List<Item> items = new ArrayList<>();
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
        items.add(item);


        Mockito.when(discountRepository.findDiscountByCode(discountRequest.getDiscountCode())).thenReturn(discount);
        discountService.addDiscountToItems(discountRequest);





    }


}
