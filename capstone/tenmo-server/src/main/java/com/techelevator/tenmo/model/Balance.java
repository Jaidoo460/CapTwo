package com.techelevator.tenmo.model;

import com.techelevator.tenmo.Exceptions.InsufficentBalanceException;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal balance;

    public BigDecimal getBalance(){
        return balance;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public void sendMoney(BigDecimal amount) throws InsufficentBalanceException{
        BigDecimal newBal = new BigDecimal(String.valueOf(balance)).subtract(amount);

        if(newBal.compareTo(BigDecimal.ZERO) >= 0){
            this.balance = newBal;
        }else{
            throw new InsufficentBalanceException();
        }
    }

    public void receiveMoney(BigDecimal amount){
        this.balance = new BigDecimal(String.valueOf(balance)).add(amount);
    }
}
