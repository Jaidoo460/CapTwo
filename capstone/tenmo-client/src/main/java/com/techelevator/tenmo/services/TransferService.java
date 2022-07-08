package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;

public interface TransferService {

    void setTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);

    Transfer[] getTransfersFromUserId(AuthenticatedUser authenticatedUser, long userId);

    Transfer getTransferFromTransferId(AuthenticatedUser authenticatedUser, long transferId);

    Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser);

    //optional part Transfer[] getPendingTransfersByUserId(AuthenticatedUser authenticatedUser);

    void updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);
}
