package com.alf.webshop.webshop.service;

import com.alf.webshop.webshop.entity.Cart;
import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import com.alf.webshop.webshop.exception.CartNotFoundException;
import com.alf.webshop.webshop.exception.UserCannotDeleteThemselfException;
import com.alf.webshop.webshop.exception.UserNotFoundException;
import com.alf.webshop.webshop.repository.CartRepository;
import com.alf.webshop.webshop.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findUserById(id);

        if (user == null) throw new UserNotFoundException();
        if (user.getId().equals(id)) throw new UserCannotDeleteThemselfException();

        Cart cart = cartRepository.findCartById(user.getCart().getId());
        if (cart == null) throw new CartNotFoundException();

        try {
            userRepository.delete(user);
            cartRepository.delete(cart);
        } catch (Exception e) {
            throw new Exception("Something went wrong");
        }

        LoggerFactory.getLogger(this.getClass()).info("USER DELETED: " + user);
    }

}
