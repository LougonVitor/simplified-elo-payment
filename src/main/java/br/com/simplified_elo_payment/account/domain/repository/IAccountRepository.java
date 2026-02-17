package br.com.simplified_elo_payment.account.domain.repository;

import java.math.BigDecimal;

public interface IAccountRepository {
    BigDecimal paymentTransaction(BigDecimal paidAmount, Long receivingUser);
    BigDecimal receiptTransaction(BigDecimal receivedAmount, Long payingUser);
}