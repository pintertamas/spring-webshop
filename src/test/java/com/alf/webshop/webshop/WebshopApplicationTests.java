package com.alf.webshop.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

@SpringBootTest
@AutoConfigureMockMvc
@ImportResource({"classpath*:application-context.xml"})
class WebshopApplicationTests {

    @Test
    void contextLoads() {
    }

}
