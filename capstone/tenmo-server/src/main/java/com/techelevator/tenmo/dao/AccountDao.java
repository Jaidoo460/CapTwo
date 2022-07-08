package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountDao {

    Balance getBalance(String user);

//    Account getUserById(long userId);

    Account getAccountByUserId(Long userId);

    void updateBalance(Account account);

}
