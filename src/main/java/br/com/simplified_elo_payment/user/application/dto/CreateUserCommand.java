package br.com.simplified_elo_payment.user.application.dto;

public record CreateUserCommand(String username, String email, String password, String role) {
}