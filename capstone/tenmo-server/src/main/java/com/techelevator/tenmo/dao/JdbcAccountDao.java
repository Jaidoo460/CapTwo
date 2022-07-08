package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    //    Account account = new Account();
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Balance getBalance(String user) {
    String sql = "SELECT account.balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?";
    SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user);
    Balance balance = new Balance();

    if(result.next()){
        String accountBalance = result.getString("balance");
        balance.setBalance(new BigDecimal(accountBalance));
    }
    return balance;
}

    @Override
    public Account getAccountByUserId(Long userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public void updateBalance(Account account) {
        String sql = "UPDATE amount SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Long accountId = rs.getLong("account_id");
        Long userAccountId = rs.getLong("user_id");

        Balance balance = new Balance();
        String accountBalance = rs.getString("balance");
        balance.setBalance(new BigDecimal(accountBalance));
        return new Account(accountId, userAccountId, balance);
        //return new Account(rs.getLong("account_id"), rs.getLong("user_id"), rs.getBigDecimal("balance"));
    }
}

//    @Override
//    public Account getBalance() {
//        Account bigDecimal = null;
//        String sql = "SELECT * FROM account";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
//
//        if(result.next()){
//            bigDecimal = mapRow(result);
//        }
//        return bigDecimal;
//    }
//    @Override
//    public Account getUserById(long userId){
//        Account account = null;
//        String sql = "SELECT * FROM account WHERE user_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//
//        if(results.next()){
//            account = mapRow(results);
//        }
//        return account;
//    }

//    private Account mapRow(SqlRowSet rx){
//        Account act = new Account();
//        act.setAccountId(rx.getLong("account_id"));
//        act.setUserId(rx.getLong("user_id"));
//        act.setBalance(rx.getBigDecimal("balance"));
//
//        return act;
//    }

