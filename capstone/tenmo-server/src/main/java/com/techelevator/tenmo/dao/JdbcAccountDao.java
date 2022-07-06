package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{
    Account account = new Account();
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalance() {
        Account bigDecimal = null;
        String sql = "SELECT * FROM account";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        if(result.next()){
            bigDecimal = mapRow(result);
        }
        return bigDecimal;
    }
    @Override
    public Account getUserById(long userId){
        Account account = null;
        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        if(results.next()){
            account = mapRow(results);
        }
        return account;
    }

    private Account mapRow(SqlRowSet rx){
        Account act = new Account();
        act.setAccountId(rx.getLong("account_id"));
        act.setUserId(rx.getLong("user_id"));
        act.setBalance(rx.getBigDecimal("balance"));

        return act;
    }
}
