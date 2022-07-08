package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Exceptions.InsufficentBalanceException;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class TransfersController {
    private TransfersDao transfersDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransfersController(TransfersDao transfersDao, AccountDao accountDao, UserDao userDao) {
        this.transfersDao = transfersDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.POST)
    public void transfer(@RequestBody Transfers transfer, @PathVariable int id) throws InsufficentBalanceException {
        //transfersDao.setTransfer(transfer);
        transfer.setTransferTypeId("Sent");
        transfer.setTransferStatusId("Approved");
        BigDecimal amountToTransfer = transfer.getAmount();
        Account accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());

        accountFrom.getBalance().sendMoney(amountToTransfer);
        accountTo.getBalance().receiveMoney(amountToTransfer);

        transfersDao.setTransfer(transfer);

        accountDao.updateBalance(accountFrom);
        accountDao.updateBalance(accountTo);
    }
}
