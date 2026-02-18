package br.com.simplified_elo_payment.account.domain.entity;

import java.math.BigDecimal;

public class AccountEntity {
    private Long id;
    private Long userId;
    private BigDecimal balance;

    public AccountEntity(Long id, Long userId, BigDecimal balance) {
        this.setId(id);
        this.setUserId(userId);
        this.setBalance(balance);
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
}