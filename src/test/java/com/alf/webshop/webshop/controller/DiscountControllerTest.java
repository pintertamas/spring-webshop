package com.alf.webshop.webshop.controller;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.model.request.CreateDiscountRequest;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Date;



@WebMvcTest(DiscountService.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class DiscountControllerTest {
    Logger log = LoggerFactory.getLogger(DiscountControllerTest.class);
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private DiscountController discountController;

    @Mock
    private DiscountService discountService;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(discountController)
                .setControllerAdvice()
                .build();


    }
    @Test
    public void createDiscountTest()throws Exception{
        Discount d = new Discount();
        d.setId(1L);
        d.setCode("Y0VSU8");
        d.setDescription("discount desc");
        d.setDiscountPercent(5);
        d.setEndDate(new Date(2022-12-12));



        CreateDiscountRequest createDiscountRequest = new CreateDiscountRequest("y0vsu8","desc",5,new Date(2022-12-12));
        LogFactory.getLog("asd");
     //   doReturn(d).when(discountController).createDiscount(createDiscountRequest);
      //   Mockito.when(discountService.createDiscount(any(CreateDiscountRequest.class))).thenReturn(d);

        Mockito.when(discountService.createDiscount(any(CreateDiscountRequest.class))).thenReturn(d);

        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/discount/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(d)))
                .andReturn().getResponse();

        Discount discObj = objectMapper.readValue(response.getContentAsString(), Discount.class);

        assertEquals(d.getCode(), discObj.getCode());

        Mockito.verify(discountService).createDiscount(any(CreateDiscountRequest.class));


    }






}
