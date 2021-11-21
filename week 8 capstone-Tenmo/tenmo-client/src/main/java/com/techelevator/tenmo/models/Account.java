package com.techelevator.tenmo.models;

import java.util.Objects;

public class Account {

    private double balance;
    private int userId;
    private int accountId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && userId == account.userId && accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, userId, accountId);
    }
}
