package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface TransfersDao {

    List<Transfers> findAll();




    Transfers setTransfer(Transfers transfer);

    Transfers getTransferById(long transferId);

    List<Transfers> getTransfersForUser(long userId);

    //List<Transfers> getPendingTransfersForUser(Long userId);


}
