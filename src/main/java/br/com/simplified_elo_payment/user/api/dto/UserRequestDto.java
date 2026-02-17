package br.com.simplified_elo_payment.user.api.dto;

public record UserRequestDto (String username, String email, String password, String role) {}