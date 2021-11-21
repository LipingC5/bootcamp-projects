package com.techelevator;

import com.techelevator.tenmo.dao.JDBCTransferDAO;
import com.techelevator.tenmo.dao.JDBCUserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import javax.sql.DataSource;
import java.util.List;

public class JDBCTransferDAOIntegrationTest extends DAOIntegrationTest{

    private JdbcTemplate jdbcTemplate;
    private JDBCTransferDAO transferDAO;
    private JDBCUserDAO userDAO;
    private static final String PASSWORD = "IjoiUk9MRV9VU0VSI";
    private static final double BALANCE = 2000;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        transferDAO = new JDBCTransferDAO(jdbcTemplate);
    }

    @Override
    protected DataSource getDataSource() {
        return super.getDataSource();
    }

    @Test
    public void insert_transfer_test() {
        Account accountA = insertAccount("Beth");
        Account accountB = insertAccount("Bob");
        Transfer expectedTransfer = insertTransferHistoryHelper(accountA.getAccountId(), accountB.getAccountId(), 500);
        Transfer actualTransfer = transferDAO.updateBalance(getTransfer(accountA.getAccountId(), accountB.getAccountId(), 500));
        Assert.assertEquals(expectedTransfer.getAccountTo(), actualTransfer.getAccountTo());
    }

    @Test
    public void update_sender_receiver_balance () {
        Account accountA = insertAccount("Beth");
        Account accountB = insertAccount("Bob");
        accountA.setBalance(1500);
        accountB.setBalance(2500);
        transferDAO.updateBalance(getTransfer(accountA.getAccountId(), accountB.getAccountId(), 500));
        Account actualAccountA = selectAccount(accountA.getAccountId());
        Account actualAccountB = selectAccount(accountB.getAccountId());
        Assert.assertEquals(accountA, actualAccountA);
        Assert.assertEquals(accountB, actualAccountB);
    }

    @Test
    public void find_account_by_username() {

        Account account = insertAccount("John");

        int actualId = transferDAO.findAccountIdByUsername("John");

        Assert.assertEquals(account.getAccountId(), actualId);
    }

    @Test
    public void transfer_history_test() {
        Account accountA = insertAccount("Beth");
        Account accountB = insertAccount("Bob");
        insertTransferHistoryHelper(accountA.getAccountId(), accountB.getAccountId(), 500);
        insertTransferHistoryHelper(accountA.getAccountId(), accountB.getAccountId(), 1000);
        List<Transfer> actualResult = transferDAO.transferHistory(accountA.getAccountId());
        Assert.assertEquals(2, actualResult.size());
    }

    private Transfer insertTransferHistoryHelper(long accountFrom, long accountTo, double amount) {
        Transfer transfer = new Transfer();
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
        Long transferId = jdbcTemplate.queryForObject(sql, Long.class, accountFrom, accountTo, amount);
        transfer.setTransferId(transferId);
        transfer.setTransferStatusId(2);
        transfer.setTransferTypeId(2);
        transfer.setAccountFrom(accountFrom);
        transfer.setAccountTo(accountTo);
        transfer.setAmount(amount);
     return transfer;
    }

    private Transfer getTransfer(long accountFrom, long accountTo, double amount) {
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(2);
        transfer.setTransferStatusId(2);
        transfer.setAccountFrom(accountFrom);
        transfer.setAccountTo(accountTo);
        transfer.setAmount(amount);
        return transfer;
    }

    private Account insertAccount(String name) {
        String sql = "INSERT INTO users (user_id, username, password_hash) " +
        "VALUES(DEFAULT, ?, ?) RETURNING user_id";
        Integer userId = jdbcTemplate.queryForObject(sql, Integer.class, name, PASSWORD);

       String sqlAccount = "INSERT INTO accounts (account_id, user_id, balance) VALUES (DEFAULT, ?, ?) RETURNING account_id";
       Integer accountId = jdbcTemplate.queryForObject(sqlAccount, Integer.class, userId, BALANCE);

       Account account = new Account();
       account.setAccountId(accountId);
       account.setUserId(userId);
       account.setBalance(BALANCE);

        return account;
    }

    private Account selectAccount (long accountId) {
        Account selectedAccount = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while (results.next()){
            selectedAccount = new Account();
            selectedAccount.setAccountId(results.getInt("account_id"));
            selectedAccount.setUserId(results.getInt("user_id"));
            selectedAccount.setBalance(results.getDouble("balance"));
        }
        return selectedAccount;
    }




}
