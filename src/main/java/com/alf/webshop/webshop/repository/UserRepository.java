package com.alf.webshop.webshop.repository;

import com.alf.webshop.webshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUsername(String username);
    ArrayList<User> findUsersByLastLoginTimeBefore(Date lastLoginTimeBefore);
}