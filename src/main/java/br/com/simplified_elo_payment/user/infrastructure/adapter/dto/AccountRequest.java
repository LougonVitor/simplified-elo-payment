package br.com.simplified_elo_payment.user.infrastructure.adapter.dto;

import java.math.BigDecimal;

public record AccountRequest(BigDecimal initialBalance, Long userId) {}