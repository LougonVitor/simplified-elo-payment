package br.com.simplified_elo_payment.account.api.dto;

import java.math.BigDecimal;

public record AccountResquestDto(BigDecimal amount, Long UserId) {}