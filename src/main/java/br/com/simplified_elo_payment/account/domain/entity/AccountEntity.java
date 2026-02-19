package br.com.simplified_elo_payment.account.domain.entity;

import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;

import java.math.BigDecimal;
import java.util.Set;

public class AccountEntity {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private Set<PaymentType> paymentType;

    public AccountEntity() {

    }

    public AccountEntity(Long id, Long userId, BigDecimal balance) {
        this.setId(id);
        this.setUserId(userId);
        this.setBalance(balance);
    }

    public AccountEntity(Long id, Long userId, BigDecimal balance, Set<PaymentType> paymentType) {
        this.setId(id);
        this.setUserId(userId);
        this.setBalance(balance);
        this.setPaymentType(paymentType);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<PaymentType> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Set<PaymentType> paymentType) {
        this.paymentType = paymentType;
    }
}