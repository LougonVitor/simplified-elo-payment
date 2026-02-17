package br.com.simplified_elo_payment.user.application.dto;

public record CreateUserComand(String username, String email, String password, String role) {
}