package com.techelevator.tenmo.model;

import com.techelevator.tenmo.Exceptions.InsufficentBalanceException;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private Long accountId;
    private Long userId;
    private Balance balance;

    public Account(){

    }

    public Account(Long accountId, Long userId, Balance balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public Balance getBalance() {
        return balance;
    }

//    public void transfer(Account accountTo, Balance amountToTransfer) throws InsufficentBalanceException {
//        if(this.balance.compareTo(amountToTransfer) >= 0){
//            this.balance = this.balance.subtract(amountToTransfer);
//            accountTo.balance = accountTo.balance.add(amountToTransfer);
//        }else {
//            throw new InsufficentBalanceException();
//        }
//    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                userId == account.userId &&
                balance.equals(account.balance);
    }


    public int hasCode() { return Objects.hash(accountId, userId, balance); }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }
    //    public void setAccountId(Long accountId) {
//        AccountId = accountId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public void setBalance(BigDecimal balance) {
//        this.balance = balance;
//    }
}
