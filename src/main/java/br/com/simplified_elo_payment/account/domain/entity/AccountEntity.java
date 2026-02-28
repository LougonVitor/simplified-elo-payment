package br.com.simplified_elo_payment.account.domain.entity;

import br.com.simplified_elo_payment.account.domain.exceptions.PaymentTypeNotAcceptedException;
import br.com.simplified_elo_payment.account.domain.exceptions.InsufficientBalanceException;
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

    //Constructor for tests
    public AccountEntity(Long userId, BigDecimal initialValue) {
        this.setUserId(userId);
        this.setBalance(initialValue);
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

    public void withdraw(BigDecimal value) {
        BigDecimal currentBalance = this.getBalance();
        this.setBalance(currentBalance.subtract(value));
    }

    public void deposit(BigDecimal value) {
        BigDecimal currentBalance = this.getBalance();
        this.setBalance(currentBalance.add(value));
    }

    public boolean canWithdraw(BigDecimal amount) {
        if(this.getBalance().compareTo(amount) >= 0) return true;

        throw new InsufficientBalanceException("Insufficient balance! Balance: " + amount.toString());
    }

    public boolean canUsePaymentType(String requeridPaymentType, Set<PaymentType> userPaymentTypes) {
        if(userPaymentTypes.contains(PaymentType.valueOf(requeridPaymentType))) return true;
        throw new PaymentTypeNotAcceptedException("The receiver cannot accept this payment type");
    }
}