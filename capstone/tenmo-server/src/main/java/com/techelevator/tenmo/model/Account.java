package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private Long AccountId;
    private Long userId;
    private BigDecimal balance;

    public Account(){

    }

    public Account(Long accountId, Long userId, BigDecimal balance) {
        AccountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Long getAccountId() {
        return AccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setAccountId(Long accountId) {
        AccountId = accountId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
