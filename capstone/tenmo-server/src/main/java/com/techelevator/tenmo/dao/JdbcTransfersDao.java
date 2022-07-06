package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransfersDao implements TransfersDao {
    private AccountDao accountDao;
    private JdbcTemplate jdbcTemplate;

    public JdbcTransfersDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    @Override
    public Transfers setTransfer(Transfers transfer) {
        
        //TODO : error with sql and or update.
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), accountDao.getUserById(transfer.getAccountFrom().getUserId()), accountDao.getUserById(transfer.getAccountTo().getUserId()), transfer.getAmount());
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,transfer);

        if(result.next()){
            transfer = mapToRow(result);
        }
        return transfer;
    }

    @Override
    public void seeTransfer() {

    }

    @Override
    public void seeTransferById(long transferId) {
        Transfers transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";

    }

    private Transfers mapToRow (SqlRowSet sr){
        Transfers tr = new Transfers();
        tr.setTransferId(sr.getLong("transfer_id"));
        tr.setTransferTypeId(sr.getLong("transfer_type_id"));
        tr.setTransferStatusId(sr.getLong("transfer_status_id"));
        tr.setAccountFrom(accountDao.getUserById(sr.getLong("account_from")));
        tr.setAccountTo(accountDao.getUserById(sr.getLong("account_to")));
        tr.setAmount(sr.getBigDecimal("amount"));

        return tr;
    }
}
