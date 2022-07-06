package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class TransfersController {
    private TransfersDao transfersDao;

    public TransfersController(TransfersDao transfersDao) {
        this.transfersDao = transfersDao;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void transfer(@Valid Transfers transfer) {
        transfersDao.setTransfer(transfer);
    }
}
