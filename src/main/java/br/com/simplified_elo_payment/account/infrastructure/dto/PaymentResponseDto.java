package br.com.simplified_elo_payment.account.infrastructure.dto;

import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;

public record PaymentResponseDto(AccountJpaEntity receiver, AccountJpaEntity payer) {}