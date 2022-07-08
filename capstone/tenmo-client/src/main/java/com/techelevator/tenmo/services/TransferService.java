package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;

public interface TransferService {

    Transfer setTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);

    Transfer[] getTransfersFromUserId(AuthenticatedUser authenticatedUser, long userId);

    Transfer getTransferFromTransferId(AuthenticatedUser authenticatedUser, long transferId);

    Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser);



}
