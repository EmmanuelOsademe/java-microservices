package com.emmydev.customer.model;

import com.emmydev.customer.validation.annotations.PasswordMatches;
import com.emmydev.customer.validation.annotations.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatches
public class CustomerRegistrationRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    @ValidEmail
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Password confirmation cannot be blank")
    private String passwordConfirmation;
}
