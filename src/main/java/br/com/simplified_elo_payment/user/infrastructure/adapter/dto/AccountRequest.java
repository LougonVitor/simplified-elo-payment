package br.com.simplified_elo_payment.user.infrastructure.adapter.dto;

import java.math.BigDecimal;
import java.util.Set;

public record AccountRequest(Long userId, BigDecimal initialBalance, Set<String> paymentTypes) {}