package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface TransfersDao {

    List<Transfers> findAll();


    void setTransfer(Transfers transfer);

    Transfers getTransferId(Long transferId);

    List<Transfers> getTransfersForUser(Long userId);

    List<Transfers> getPendingTransfersForUser(Long userId);

    void updateStatus(Transfers transfer);


//    Transfers setTransfer(Transfers transfer);

//    void seeTransfer();

//    void seeTransferById(long transferId);
}
