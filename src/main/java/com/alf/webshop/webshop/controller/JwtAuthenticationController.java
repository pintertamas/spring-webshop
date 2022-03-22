package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.UserAlreadyExistsException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.model.request.IdRequest;
import com.alf.webshop.webshop.model.request.JwtRequest;
import com.alf.webshop.webshop.model.response.JwtResponse;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.model.response.UserResponse;
import com.alf.webshop.webshop.repository.UserRepository;
import com.alf.webshop.webshop.service.UserService;
import org.apache.juli.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        User user = userRepository.findUserByUsername(authenticationRequest.getUsername());
        String token;
        try {
            token = userService.login(authenticationRequest);
        } catch (LoginException e) {
            LogFactory.getLog(this.getClass()).error("ERROR AT LOGIN: " + user.getLastLoginTime() + " " + user);
            return new ResponseEntity<>("Could not reach database", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LogFactory.getLog(this.getClass()).info("NEW LOGIN: " + user.getLastLoginTime() + " " + user);
        return ResponseEntity.ok(new JwtResponse(token, new UserResponse(user)));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User newUser) {
        try {
            User user = userService.register(newUser);
            LoggerFactory.getLogger(this.getClass()).info("USER CREATED: " + newUser);
            return ResponseEntity.ok(new UserResponse(user));
        } catch (UserAlreadyExistsException uaee) {
            LoggerFactory.getLogger(this.getClass()).error("USER ALREADY EXISTS: " + uaee.getExistingUser());
            return ResponseEntity.badRequest().body(uaee.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestBody IdRequest request) {
        try {
            userService.deleteUser(request.getId());
        } catch (UserCannotDeleteThemselfException ucdt) {
            LoggerFactory.getLogger(this.getClass()).error("USER CANNOT DELETE THEMSELF: " + ucdt.getUser());
            return new ResponseEntity<>(ucdt.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException unfe) {
            LoggerFactory.getLogger(this.getClass()).error("USER WITH ID: " + unfe.getUserId() + " COULD NOT BE FOUND");
            return new ResponseEntity<>(unfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CartNotFoundException cnfe) {
            LoggerFactory.getLogger(this.getClass()).error("CART COULD NOT BE FOUND");
            return new ResponseEntity<>(cnfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LoggerFactory.getLogger(this.getClass()).info("USER DELETED");
        return ResponseEntity.ok("User with " + request.getId() + " was deleted successfully!");
    }
}