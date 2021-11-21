package com.techelevator;

import com.techelevator.tenmo.dao.JDBCAccountDAO;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JDBCAccountDAOIntegrationTest extends DAOIntegrationTest {

    private JdbcTemplate jdbcTemplate;
    private JDBCAccountDAO accountDAO;
    private static final int USER_ID = 123;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        accountDAO = new JDBCAccountDAO(jdbcTemplate);

        String sql = "INSERT INTO users(user_id, username, password_hash) VALUES (?, ?, ?) ";
        jdbcTemplate.update(sql, USER_ID, "Brian", "IjoiUk9MRV9VU0VSI");
    }

    @Override
    protected DataSource getDataSource() {
        return super.getDataSource();
    }

    @Test
    public void get_balance(){
        insertAccount(USER_ID, 1234);

        Account actualAccount = accountDAO.getBalance(USER_ID, "Brian" );

        Assert.assertEquals(1234, actualAccount.getBalance(), 0.009);
    }

    private Account insertAccount(int userId, double balance){
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(balance);
        String sql = "INSERT INTO accounts (account_id, user_id, balance)" +
                "VALUES (DEFAULT, ?, ?) RETURNING account_id ";
        Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class, userId, balance);
        account.setAccountId(accountId);
        return account;
    }

}
