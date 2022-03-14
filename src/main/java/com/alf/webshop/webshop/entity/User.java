package com.alf.webshop.webshop.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @NotNull(message = "Role should not be null")
    @Column(name = "role")
    private Role role;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should only contain letters between a-z, A-Z and numbers between 0-9")
    @Column(name = "username", unique = true)
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, max = 60)
    @Pattern(regexp = "(?=(.*[0-9]))((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z]))^.{8,}$", message = "Password must have 1 lowercase letter, 1 uppercase letter, 1 number, and be at least 8 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Email is cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "Telephone cannot be null")
    @NotBlank(message = "Telephone cannot be blank")
    @Pattern(regexp = "[+]?[0-9]{1,3}[0-9]{1,2}[0-9]{7}", message = "Telephone number should be a valid number and cannot be separated with symbols or spaces. Like +36201234567 or 0611234567")
    @Column(name = "telephone")
    private String telephone;

    @OneToOne
    @JoinColumn(name = "cart")
    private Cart cart;

    @Column(name = "last_login")
    private Date lastLoginTime;
}