package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    ArrayList<User> findUsersByLastLoginTimeBeforeAndRoleIsNot(Date lastLoginTimeBefore, Role notThisRole);
}