package br.com.simplified_elo_payment.user.application.dto;

import java.util.Set;

public record CreateUserCommand(String username, String email, String password, String role, String initialBalance, Set<String> paymentTypes) {
}