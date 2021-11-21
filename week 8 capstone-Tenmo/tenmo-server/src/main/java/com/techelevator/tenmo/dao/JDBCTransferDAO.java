package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class JDBCTransferDAO implements TransferDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCTransferDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer updateBalance(Transfer transfer) {
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (DEFAULT,?, ?, ?, ?, ?) " +
                "RETURNING transfer_id";
        Long transferId = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        transfer.setTransferId(transferId);

        String sqlUpdateSender = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        jdbcTemplate.update(sqlUpdateSender, transfer.getAmount(), transfer.getAccountFrom());

        String sqlUpdateReceiver = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        jdbcTemplate.update(sqlUpdateReceiver, transfer.getAmount(), transfer.getAccountTo());

        return transfer;
    }

    @Override
    public int findAccountIdByUsername(String username) {
        String sql = "SELECT account_id FROM accounts " +
                "JOIN users ON accounts.user_id = users.user_id " +
                "WHERE username = ?";
        Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return accountId;
    }

    @Override
    public List<Transfer> transferHistory(int accountId){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, users.username AS username " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "JOIN users ON accounts.user_id = users.user_id " +
                "WHERE account_from = ? OR account_to = ? ";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while(rows.next()){
            transfers.add(mapRowToTransfer(rows));
        }
        return transfers;
    }

    @Override
    public List<Transfer> pendingRequests(int accountId){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, users.username AS username " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_to = accounts.account_id " +
                "JOIN users ON accounts.user_id = users.user_id " +
                "WHERE transfer_status_id = ? AND account_to = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, 1, accountId);
        while(rows.next()){
            transfers.add(mapRowToTransfer(rows));
        }
        return transfers;
    }

     private Account mapRowToAccount(SqlRowSet result){
        Account account = new Account();
        account.setBalance(result.getDouble("balance"));
        return account;
     }

    private Transfer mapRowToTransfer(SqlRowSet row) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(row.getLong("transfer_id"));
        transfer.setTransferTypeId(row.getLong("transfer_type_id"));
        transfer.setTransferStatusId(row.getLong("transfer_status_id"));
        transfer.setAccountFrom(row.getLong("account_from"));
        transfer.setAccountTo(row.getLong("account_to"));
        transfer.setAmount(row.getDouble("amount"));
        transfer.setUserName(row.getString("username"));
        return transfer;
    }

}
