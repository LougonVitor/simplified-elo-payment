package br.com.simplified_elo_payment.account.domain.repository;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;

import java.math.BigDecimal;
import java.util.Set;

public interface IAccountRepository {
    BigDecimal paymentTransaction(BigDecimal paidAmount, Long receivingUserId);
    BigDecimal receiptTransaction(BigDecimal receivedAmount, Long payingUserId);
    AccountEntity findAccountByUserId(Long userId);
    BigDecimal updateAccountBalance(AccountEntity accountUpdated);
    Long createNewAccount(BigDecimal initialValue, Long userId, Set<PaymentType> paymentTypes);
}