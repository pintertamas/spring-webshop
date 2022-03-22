package com.alf.webshop.webshop.model.response;

import com.alf.webshop.webshop.entity.Role;
import com.alf.webshop.webshop.entity.User;
import lombok.Getter;

import java.sql.Date;

@Getter
public class UserResponse {
    private final Long id;
    private final Role role;
    private final String username;
    private final String email;
    private final String telephone;
    private final Date lastLoginTime;
    private final boolean isDeleted;

    public UserResponse(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.lastLoginTime = user.getLastLoginTime();
        this.isDeleted = user.isDeleted();
    }
}
