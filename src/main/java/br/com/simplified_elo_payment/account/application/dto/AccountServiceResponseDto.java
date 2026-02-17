package br.com.simplified_elo_payment.account.application.dto;

import java.math.BigDecimal;

public record AccountServiceResponseDto(BigDecimal newBalance) {}