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
    public Account getAccountByAccountId(long accountId){
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);

        if(result.next()){
            account = mapRowToAccount(result);
        }
        return account;

    }

    @Override
    public Account getAccountByUserId(long userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if(result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public void updateBalance(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getBalance().getBalance(), account.getAccountId());
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


