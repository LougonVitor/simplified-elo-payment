package br.com.simplified_elo_payment.account.domain.repository;

import java.math.BigDecimal;

public interface IAccountRepository {
    BigDecimal paymentTransaction(BigDecimal amountPaid);
    BigDecimal receiptTransaction(BigDecimal amountReceived);
}