package br.com.simplified_elo_payment.account.api.dto.creation;

import java.math.BigDecimal;
import java.util.Set;

public record CreationResquestDto(BigDecimal initialBalance, Long userId, Set<String> paymentTypes) {}