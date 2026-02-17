package br.com.simplified_elo_payment.account.domain.entity;

import java.math.BigDecimal;

public class AccountEntity {
    private Long Id;
    private Long UserId;
    private BigDecimal Balance;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public BigDecimal getBalance() {
        return Balance;
    }

    public void setBalance(BigDecimal balance) {
        Balance = balance;
    }
}