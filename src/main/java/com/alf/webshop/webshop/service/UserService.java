package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.config.JwtTokenUtil;
import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.UserAlreadyExistsException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.exception.UsernameIsTakenException;
import com.alf.webshop.webshop.model.request.JwtRequest;
import com.alf.webshop.webshop.model.request.PasswordRequest;
import com.alf.webshop.webshop.model.request.UserEditRequest;
import com.alf.webshop.webshop.model.response.JwtResponse;
import com.alf.webshop.webshop.model.response.UserResponse;
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

import javax.naming.AuthenticationException;
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

    public void disableUser(User user) {
        user.setDeleted(true);
        user.setRole(Role.DELETED);
        userRepository.save(user);
    }

    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findUserById(id);
        String token = JwtTokenUtil.getToken();
        User currentUser = jwtTokenUtil.getUserFromToken(token);

        if (user == null) throw new UserNotFoundException(id);
        if (currentUser.getId().equals(id)) throw new UserCannotDeleteThemselfException(user);

        try {
            disableUser(user);
        } catch (Exception e) {
            throw new Exception("Something went wrong");
        }
    }

    public JwtResponse login(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = jwtTokenUtil.getUserFromToken(token);
        long millis = System.currentTimeMillis();
        Date lastLogin = new Date(millis);
        user.setLastLoginTime(lastLogin);
        userRepository.save(user);
        return new JwtResponse(token, new UserResponse(user));
    }

    public User register(User newUser) throws UserAlreadyExistsException {
        if (userRepository.findUserByUsername(newUser.getUsername()) != null) {
            throw new UserAlreadyExistsException(newUser);
        }
        newUser.setRole(Role.USER);
        Cart cart = new Cart();
        newUser.setCart(cart);
        cartRepository.save(cart);
        newUser.setDeleted(false);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        // mainly for testing purposes
        if (newUser.getRole().equals(Role.DELETED)) {
            newUser.setDeleted(true);
        }
        return newUser;
    }

    public User validateUserId(Long id) throws AuthenticationException, UserNotFoundException {
        String token = JwtTokenUtil.getToken();
        User currentUser = jwtTokenUtil.getUserFromToken(token);
        User user = userRepository.findUserById(id);
        if (user == null) throw new UserNotFoundException(id);

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            if (!currentUser.equals(user)) {
                throw new AuthenticationException("Authentication exception");
            }
        }
        return user;
    }

    public User editUserData(User user, UserEditRequest userEditRequest) throws UserNotFoundException, UsernameIsTakenException {
        if (userEditRequest.getUsername() != null) {
            User userWithSameUsername = userRepository.findUserByUsername(userEditRequest.getUsername());
            if (userWithSameUsername != null) throw new UsernameIsTakenException(userEditRequest.getUsername());
            user.setUsername(userEditRequest.getUsername());
        }
        if (userEditRequest.getEmail() != null) user.setEmail(userEditRequest.getEmail());
        if (userEditRequest.getTelephone() != null) user.setTelephone(userEditRequest.getTelephone());
        return userRepository.save(user);
    }

    public void editPassword(User user, PasswordRequest passwordRequest) {
        user.setPassword(passwordRequest.getPassword());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addAsAdmin(Long id) throws UserNotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null) throw new UserNotFoundException(id);
        if (user.getRole().equals(Role.DELETED)) {
            user.setDeleted(false);
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    public void addAsUser(Long id) throws UserNotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null) throw new UserNotFoundException(id);
        if (user.getRole().equals(Role.DELETED)) {
            user.setDeleted(false);
        }
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
