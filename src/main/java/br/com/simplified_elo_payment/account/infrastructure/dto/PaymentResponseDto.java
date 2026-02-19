package br.com.simplified_elo_payment.account.infrastructure.dto;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;

public record PaymentResponseDto(AccountEntity receiver, AccountEntity payer) {}