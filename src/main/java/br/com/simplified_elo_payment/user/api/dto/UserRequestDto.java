package br.com.simplified_elo_payment.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequestDto (
        @NotBlank(message = "Username cannot be null")
        String username

        , @NotBlank(message = "Email cannot be null")
        @Email(message = "The email is not valid")
        String email

        , @NotBlank(message = "Password cannot be null")
        String password

        , @NotBlank(message = "Role cannot be null")
        String role

        , String initialBalance

        , @NotEmpty(message = "The payment types list cannot be empty")
        Set<String> paymentTypes
) {}