package com.alf.webshop.webshop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    @NotBlank(message = "Username cannot be blank")
    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should only contain letters between a-z, A-Z and numbers between 0-9")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Telephone cannot be blank")
    @Pattern(regexp = "[+]?[0-9]{1,3}[0-9]{1,2}[0-9]{7}", message = "Telephone number should be a valid number and cannot be separated with symbols or spaces. Like +36201234567 or 0611234567")
    @Column(name = "telephone")
    private String telephone;
}
