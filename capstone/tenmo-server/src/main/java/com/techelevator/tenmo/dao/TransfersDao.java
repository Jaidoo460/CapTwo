package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;

public interface TransfersDao {

    Transfers setTransfer(Transfers transfer);

    void seeTransfer();

    void seeTransferById(long transferId);
}
