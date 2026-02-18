package br.com.simplified_elo_payment.user.api.dto;

import java.util.Set;

public record UserRequestDto (String username, String email, String password, String role, String initialBalance, Set<String> paymentTypes) {}