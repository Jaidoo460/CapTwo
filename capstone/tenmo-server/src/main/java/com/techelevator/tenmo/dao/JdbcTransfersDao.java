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
import java.util.List;

@Component
public class JdbcTransfersDao implements TransfersDao {

    private Logger log = LoggerFactory.getLogger(getClass());
    private AccountDao accountDao;
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

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
        List<Transfers> transfer = new ArrayList<>();
        SqlRowSet result = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER);
        while (result.next()) {
            Transfers transfers = mapRowToTransfer(result);
            transfer.add(transfers);
        }
        return transfer;
    }

    public Transfers getTransferId(Long transferId) {
        Transfers transfer = null;
        String sql = SQL_SELECT_TRANSFER + "WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public Transfers setTransfer(Transfers newTransfer) {

        //TODO : error with sql and or update.
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?, ?)";

        Long newTransferId = getNextTransferId();
        Transfers transferTypeId = getTransferTypeId(newTransfer.getTransferTypeId());
        //^^Used to be a long
        Transfers transferStatusId = getTransferStatusId(newTransfer.getTransferStatusId());
        //^^Used to be a long
        Account fromAccount = accountDao.getAccountByUserId(newTransfer.getAccountFrom());
        Account toAccount = accountDao.getAccountByUserId(newTransfer.getAccountTo());

        jdbcTemplate.update(sql, newTransferId, transferTypeId, transferStatusId, fromAccount.getAccountId(), toAccount.getAccountId(), newTransfer.getAmount());
        //jdbcTemplate.update(sql,newTransfer.getTransferId(), newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
        log.debug("created new Transfer with ID: " + newTransferId);

        return getTransferId(newTransferId);
    }


//        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), accountDao.getUserById(transfer.getAccountFrom().getUserId()), accountDao.getUserById(transfer.getAccountTo().getUserId()), transfer.getAmount());
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,transfer);

//        if(result.next()){
//            transfer = mapToRow(result);
//        }
//        return transfer;


    @Override
    public List<Transfers> getTransfersForUser(Long userId) {
        List<Transfers> transfer = new ArrayList<>();
        String sql = SQL_SELECT_TRANSFER + "" +
                "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR account_to IN (SELECT account_id FROM account WHERE user_id = ?))";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (result.next()) {
            Transfers transfers = mapRowToTransfer(result);
            transfer.add(transfers);
        }
        return transfer;
    }


    @Override
    public List<Transfers> getPendingTransfersForUser(Long userId) {
        List<Transfers> transfer = new ArrayList<>();
        String sql = SQL_SELECT_TRANSFER + "WHERE transfer_status_id = 1 AND account_from IN (SELECT account_id FROM account WHERE user_id = ?)";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while (result.next()) {
            Transfers transfers = mapRowToTransfer(result);
            transfer.add(transfers);
        }
        return transfer;

    }

    @Override
    public void updateStatus(Transfers transfer){
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        //Long transferStatusId = getTransferStatusId(transfer.getTransferStatus());
        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
    }

    private long getNextTransferId(){
        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
        if(nextIdResult.next()){
            return nextIdResult.getLong(1);
        }else{
            throw new RuntimeException("Something went wrong while getting an id for the new transfer");
        }
    }

    private Transfers getTransferTypeId(String transferType){
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_type WHERE  transfer_type_desc = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferType);
        Transfers transfersType = null;

        if(result.next()){
            int transferTypeId = result.getInt("transfer_type_id");
            String transferTypeDesc = result.getString("transfer_type_desc");

            transfersType = new Transfers(transferTypeId, transferTypeDesc);

        }
        return transfersType;
//        String sql = "SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = ?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferType);
//        if(result.next()){
//            return result.getLong(1);
//        }else {
//            throw new RuntimeException("Unable to lookup transferType " + transferType);
//        }
    }

   private Transfers getTransferStatusId(String transferStatus){

        String sql = "SELECT transfer_status_id, transfer_status_desc FROM transfer_status WHERE transfer_status_desc = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferStatus);

        Transfers transfersStatus = null;

        if(result.next()){
            int transferStatusId = result.getInt("transfer_status_id");
            String transferStatusDesc = result.getString("transfer_status_desc");
            transfersStatus = new Transfers(transferStatusId, transferStatusDesc);
        }
        return transfersStatus;
//        String sql = "SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = ?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferStatus);
//       if(result.next()){
//           return result.getLong(1);
//       }else {
//           throw new RuntimeException("Unable to lookup transferStatus " + transferStatus);
//       }
   }

   private Transfers mapRowToTransfer(SqlRowSet rs){
        //TODO drastic changes made!

       Long transferId = rs.getLong("transfer_id");
       String transferTypeId = rs.getString("transfer_type_id");
       String transferStatusId = rs.getString("transfer_status_id");
       Long accountFrom = rs.getLong("account_from");
       Long accountTo = rs.getLong("account_to");
       String amount = rs.getString("amount");

       Transfers transfer = new Transfers(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, new BigDecimal(amount));
       return transfer;
//        return new Transfers(rs.getLong("transfer_id"),
//                            rs.getString("transfer_type_desc"),
//                            rs.getString("transfer_status_desc"),
//                            accountDao.getAccountByUserId(rs.getLong("accountFrom")),
//                            accountDao.getAccountByUserId(rs.getLong("accountTo")),
//                            rs.getBigDecimal("amount"));
   }

}


//    public void seeTransferById(long transferId) {
//        Transfers transfer = null;
//        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
//
//    }

//    private Transfers mapToRow (SqlRowSet sr){
//        Transfers tr = new Transfers();
//        tr.setTransferId(sr.getLong("transfer_id"));
//        tr.setTransferTypeId(sr.getLong("transfer_type_id"));
//        tr.setTransferStatusId(sr.getLong("transfer_status_id"));
//        tr.setAccountFrom(accountDao.getUserById(sr.getLong("account_from")));
//        tr.setAccountTo(accountDao.getUserById(sr.getLong("account_to")));
//        tr.setAmount(sr.getBigDecimal("amount"));
//
//        return tr;

