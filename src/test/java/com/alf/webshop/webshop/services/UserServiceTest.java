package com.alf.webshop.webshop.services;
import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.*;
import com.alf.webshop.webshop.exception.EmptyListException;
import com.alf.webshop.webshop.model.request.ItemRequest;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.ImageRepository;
import com.alf.webshop.webshop.repository.ItemRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import com.alf.webshop.webshop.service.CartService;
import com.alf.webshop.webshop.service.ItemService;
import com.alf.webshop.webshop.service.JwtUserDetailsService;
import com.alf.webshop.webshop.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserService.class)
@ContextConfiguration(classes = UserService.class)
public class UserServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Before
    public void init(){
        authenticationManager=Mockito.mock(AuthenticationManager.class);
        userRepository=Mockito.mock(UserRepository.class);
        cartRepository=Mockito.mock(CartRepository.class);
        jwtTokenUtil=Mockito.mock(JwtTokenUtil.class);
        jwtUserDetailsService=Mockito.mock(JwtUserDetailsService.class);
        passwordEncoder=Mockito.mock(PasswordEncoder.class);
        userService = new UserService();
    }






}
