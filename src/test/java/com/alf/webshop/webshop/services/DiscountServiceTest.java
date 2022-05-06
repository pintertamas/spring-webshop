package com.alf.webshop.webshop.services;

import com.alf.webshop.webshop.exception.DiscountAlreadyExistsException;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.entity.Discount;
import com.alf.webshop.webshop.repository.DiscountRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@WebMvcTest(DiscountService.class)
@ContextConfiguration(classes = DiscountService.class)
public class DiscountServiceTest {


    @Mock
    private DiscountRepository discountRepository=Mockito.mock(DiscountRepository.class);
    @InjectMocks
    private final DiscountService discountService = Mockito.mock(DiscountService.class) ;



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
        discount.setCode("asd");

        Mockito.when(discountRepository.findDiscountByCode("y0vsu8")).thenReturn(discount);
        assertEquals(discount_final,discountService.createDiscount(createDiscountRequest));


    }


}
