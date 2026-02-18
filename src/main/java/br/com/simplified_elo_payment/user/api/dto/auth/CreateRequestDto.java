package br.com.simplified_elo_payment.user.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateRequestDto(String username, String email, String password, String role, String initialBalance, Set<String> paymentTypes) {}