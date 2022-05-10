package com.alf.webshop.webshop.services;

import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.repository.DiscountRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import com.alf.webshop.webshop.service.DiscountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;


//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DiscountService.class)
@ContextConfiguration(classes = DiscountService.class)
public class DiscountServiceTest {
    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private DiscountService discountService;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        //discountRepository=Mockito.mock(DiscountRepository.class);
        //itemRepository=Mockito.mock(ItemRepository.class);

        discountService=new DiscountService();
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

        Discount discount=new Discount();

        //Discount d = discountService.createDiscount(createDiscountRequest);
        Mockito.when(discountRepository.findDiscountByCode(createDiscountRequest.getCode())).thenReturn(discount);
        Discount d = discountService.createDiscount(createDiscountRequest);
        //assertEquals(discount_final,d);
        //assertEquals(discount_final,this.discountService.createDiscount(createDiscountRequest));
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
        item.setDiscount(discount);
        items.add(item);

        Mockito.when(itemRepository.save(item)).thenReturn(item);
        assertEquals(items,discountService.saveItemsWithDiscount(items,discount));





    }


}
