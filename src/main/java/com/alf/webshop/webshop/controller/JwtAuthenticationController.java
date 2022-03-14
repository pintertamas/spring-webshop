package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.model.IdRequest;
import com.alf.webshop.webshop.model.JwtRequest;
import com.alf.webshop.webshop.model.JwtResponse;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import com.alf.webshop.webshop.service.JwtUserDetailsService;
import com.alf.webshop.webshop.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

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
            LogFactory.getLog(this.getClass()).error("ERROR AT LOGIN: " + user.getLastLoginTime() + " " + user);
            return new ResponseEntity<>("Could not reach database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LogFactory.getLog(this.getClass()).info("NEW LOGIN: " + user.getLastLoginTime() + " " + user);
        return ResponseEntity.ok(new JwtResponse(token, user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody User newUser) {
        User user = userRepository.findUserByUsername(newUser.getUsername());
        if (user != null) {
            LoggerFactory.getLogger(this.getClass()).error("USER ALREADY EXISTS: " + user);
            return ResponseEntity.badRequest().body("User with this username already exists");
        }
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        newUser.setCart(newCart);

        LoggerFactory.getLogger(this.getClass()).info("USER CREATED: " + newUser);
        return ResponseEntity.ok(userDetailsService.save(newUser));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestBody IdRequest request) {

        User user = userRepository.findUserById(request.getId());
        Cart cart = cartRepository.findCartById(user.getCart().getId());
        try {
            userService.deleteUser(request.getId());
        } catch (UserCannotDeleteThemselfException ucdt) {
            LoggerFactory.getLogger(this.getClass()).error("USER CANNOT DELETE THEMSELF");
            return new ResponseEntity<>(ucdt.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException unfe) {
            LoggerFactory.getLogger(this.getClass()).error("USER WITH ID: " + request + " COULD NOT BE FOUND");
            return new ResponseEntity<>(unfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CartNotFoundException cnfe) {
            LoggerFactory.getLogger(this.getClass()).error("CART WITH ID: " + cart.getId() + " COULD NOT BE FOUND");
            return new ResponseEntity<>(cnfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LoggerFactory.getLogger(this.getClass()).info("USER DELETED: " + user);
        return ResponseEntity.ok("User was deleted successfully!");
    }
}