package com.alf.webshop.webshop.controller;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.model.response.ItemResponse;
import com.alf.webshop.webshop.service.ItemService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationContext.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    Logger log = LoggerFactory.getLogger(ItemControllerTest.class);
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,true);

        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice()
                .build();


    }
    @Test
    public void createItemTest()throws Exception{
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

        ItemResponse ir = new ItemResponse(item);

        Mockito.when(itemService.createItem(any((ItemRequest.class)))).thenReturn(item);

        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/item/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ir)))
                .andReturn().getResponse();

        Item itemObj = objectMapper.readValue(response.getContentAsString(), Item.class);


        assertEquals(Color.BLACK, itemObj.getColor());
        assertEquals(Gender.MAN, itemObj.getGender());
        assertEquals(12, itemObj.getPrice());
        assertEquals("MockDesc", itemObj.getDescription());



    }








}
