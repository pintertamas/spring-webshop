package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.UserAlreadyExistsException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.model.JwtRequest;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import org.apache.juli.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.util.ArrayList;

import static org.postgresql.gss.MakeGSS.authenticate;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findUserById(id);

        if (user == null) throw new UserNotFoundException(id);
        if (user.getId().equals(id)) throw new UserCannotDeleteThemselfException(user);

        Cart cart = cartRepository.findCartById(user.getCart().getId());
        if (cart == null) throw new CartNotFoundException(user.getCart().getId());

        try {
            userRepository.delete(user);
            cartRepository.delete(cart);
        } catch (Exception e) {
            throw new Exception("Something went wrong");
        }
    }

    public String login(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = jwtTokenUtil.getUserFromToken(token);
        long millis = System.currentTimeMillis();
        Date lastLogin = new Date(millis);
        user.setLastLoginTime(lastLogin);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new LoginException();
        }
        return token;
    }

    public void register(User newUser) throws UserAlreadyExistsException {
        User user = userRepository.findUserByUsername(newUser.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException(user);
        }
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        newUser.setCart(newCart);
    }
}
