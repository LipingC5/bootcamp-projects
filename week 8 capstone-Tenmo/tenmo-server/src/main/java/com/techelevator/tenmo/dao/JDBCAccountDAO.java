package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCAccountDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalance(int userId, String userName) {
        Account account = null;
        String sql = "SELECT accounts.account_id, users.user_id, balance FROM accounts " +
                "JOIN users ON users.user_id = accounts.user_id " +
                "WHERE accounts.user_id = ? AND users.username = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, userName);
        while(rows.next()) {
            account = mapRowToAccount(rows);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet row) {
        Account account = new Account();
        account.setAccountId(row.getInt("account_id"));
        account.setUserId(row.getInt("user_id"));
        account.setBalance(row.getDouble("balance"));
        return account;
    }

}
