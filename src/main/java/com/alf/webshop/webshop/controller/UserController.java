package com.alf.webshop.webshop.controller;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.exception.*;
import com.alf.webshop.webshop.model.request.JwtRequest;
import com.alf.webshop.webshop.model.request.UserEditRequest;
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

import javax.naming.AuthenticationException;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editUser(@Valid @PathVariable Long id, @RequestBody UserEditRequest editedUser) {
        try {
            String token = JwtTokenUtil.getToken();
            User currentUser = jwtTokenUtil.getUserFromToken(token);
            User user = userRepository.findUserById(id);
            if (!currentUser.getRole().equals(Role.ADMIN)) {
                if (!currentUser.equals(user)) {
                    throw new AuthenticationException("This user cannot edit the user with the given userId");
                }
            }
            user = userService.editUserData(id, editedUser);

            LoggerFactory.getLogger(this.getClass()).info("USER EDITED: " + user);
            return ResponseEntity.ok(new UserResponse(user));
        } catch (AuthenticationException ae) {
            LoggerFactory.getLogger(this.getClass()).error("USER CANNOT EDIT USER WITH ID: " + id);
            return ResponseEntity.badRequest().body(ae.getMessage());
        } catch (UserNotFoundException unfe) {
            LoggerFactory.getLogger(this.getClass()).error("USER WITH ID: " + unfe.getUserId() + " COULD NOT BE FOUND");
            return new ResponseEntity<>(unfe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UsernameIsTakenException uite) {
            LoggerFactory.getLogger(this.getClass()).error("USERNAME (" + editedUser.getUsername() + " IS ALREADY TAKEN");
            return new ResponseEntity<>(uite.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
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
        return ResponseEntity.ok("User with " + id + " was deleted successfully!");
    }
}