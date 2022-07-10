package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountService {
    BigDecimal getBalance(AuthenticatedUser authenticatedUser);

    Account getAccountByUserId(AuthenticatedUser authenticatedUser, long userId);

    Account getAccountById(AuthenticatedUser authenticatedUser, long accountId);
}
