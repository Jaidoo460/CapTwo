package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountDao {

    Balance getBalance(String user);

    Account getAccountByAccountId(long accountId);

    Account getAccountByUserId(long userId);

    void updateBalance(Account account);

}
