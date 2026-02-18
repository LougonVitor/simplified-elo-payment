package br.com.simplified_elo_payment.account.api.dto.creation;

import java.util.Set;

public record AccountCreationResquestDto(String initialBalance, Long userId, Set<String> paymentTypes) {}