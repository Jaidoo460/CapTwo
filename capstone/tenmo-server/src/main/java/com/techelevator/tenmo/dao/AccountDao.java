package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account getBalance();

    BigDecimal setTransfer(String user, BigDecimal amountToSend);

    void seeTransfer();

    void seeTransferById(long transferId);





}
