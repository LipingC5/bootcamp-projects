package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDAO {

    Transfer updateBalance(Transfer transfer);
    List<Transfer> transferHistory(int accountId);
    int findAccountIdByUsername(String username);
    List<Transfer> pendingRequests(int accountId);

}
