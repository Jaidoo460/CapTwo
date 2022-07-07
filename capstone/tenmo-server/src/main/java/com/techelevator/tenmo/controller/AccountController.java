package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransfersDao transfersDao;

    public AccountController(AccountDao accountDao, UserDao userDao, TransfersDao transfersDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transfersDao = transfersDao;
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) throws UsernameNotFoundException {
        Long userId = getCurrentUserId(principal);
        return accountDao.getAccountByUserId(userId).getBalance();
    }

    @RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public List<Transfers> getTransfers(Principal principal){
        return transfersDao.getTransfersForUser(getCurrentUserId(principal));
    }

    private Long getCurrentUserId(Principal principal) {
        return userDao.findByUsername(principal.getName()).getId();
    }
}
