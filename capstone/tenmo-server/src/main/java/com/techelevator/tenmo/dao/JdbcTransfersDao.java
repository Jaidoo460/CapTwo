package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JdbcTransfersDao implements TransfersDao {

    private Logger log = LoggerFactory.getLogger(getClass());
    private AccountDao accountDao;
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private List<Transfers> transfers = new ArrayList<>();


    private static final String SQL_SELECT_TRANSFER = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, " +
            "aFrom.account_id as fromAcct, aFrom.user_id as fromUser, aFrom.balance as fromBal, " +
            "aTo.account_id as toAcct, aTo.user_id as toUser, aTo.balance as toBal " +
            "FROM transfer t " +
            "INNER JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
            "INNER JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
            "INNER JOIN account aFrom on account_from = aFrom.account_id " +
            "INNER JOIN account aTo on account_to = aTo.account_id ";

    public JdbcTransfersDao(JdbcTemplate jdbcTemplate, AccountDao accountDao, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @Override
    public List<Transfers> findAll() {
        List<Transfers> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public Transfers getTransferById(long transferId) {
        Transfers transfer = null;
        String sql = SQL_SELECT_TRANSFER + "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfers setTransfer(Transfers newTransfer) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,2,2,newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());

        return newTransfer;
    }

    @Override
    public List<Transfers> getTransfersForUser(long userId) {

        List<Transfers> transfers = new ArrayList<>();
        String sql = SQL_SELECT_TRANSFER + "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR account_to IN (SELECT account_id FROM account WHERE user_id = ?))";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

   private Transfers mapRowToTransfer(SqlRowSet rs){
        //TODO drastic changes made!
       Transfers transfer = new Transfers();
       transfer.setTransferId(rs.getLong("transfer_id"));
       transfer.setTransferTypeId(rs.getString("transfer_type_desc"));
       transfer.setTransferStatusId(rs.getString("transfer_status_desc"));
       transfer.setAccountFrom(rs.getLong("fromAcct"));
       transfer.setAccountTo(rs.getLong("toAcct"));
       transfer.setAmount(rs.getBigDecimal("amount"));
       return transfer;
   }
}

