package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.UserAlreadyExistsException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.model.request.JwtRequest;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.sql.Date;

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
    private PasswordEncoder bcryptEncoder;

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
        String token = JwtTokenUtil.getToken();
        User currentUser = jwtTokenUtil.getUserFromToken(token);

        if (user == null) throw new UserNotFoundException(id);
        if (currentUser.getId().equals(id)) throw new UserCannotDeleteThemselfException(user);

        try {
            user.setDeleted(true);
            user.setRole(Role.DELETED);
            userRepository.save(user);
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

    public User register(User newUser) throws UserAlreadyExistsException {
        if (userRepository.findUserByUsername(newUser.getUsername()) != null) {
            throw new UserAlreadyExistsException(newUser);
        }
        Cart cart = new Cart();
        newUser.setCart(cart);
        newUser.setDeleted(false);
        cartRepository.save(cart);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        // mainly for testing purposes
        if (newUser.getRole().equals(Role.DELETED)) {
            newUser.setDeleted(true);
        }

        return newUser;
    }
}
