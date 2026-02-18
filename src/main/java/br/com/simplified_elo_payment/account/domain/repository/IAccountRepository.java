package br.com.simplified_elo_payment.account.domain.repository;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;

import java.math.BigDecimal;
import java.util.Set;

public interface IAccountRepository {
    PaymentResponseDto transaction(AccountEntity receiver, AccountEntity payer);
    AccountJpaEntity findAccountByUserId(Long userId);
    AccountJpaEntity updateAccountBalance(AccountEntity accountUpdated);
    Long createNewAccount(BigDecimal initialValue, Long userId, Set<PaymentType> paymentTypes);
}