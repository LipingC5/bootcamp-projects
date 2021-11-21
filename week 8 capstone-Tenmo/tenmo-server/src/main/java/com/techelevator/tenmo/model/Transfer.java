package com.techelevator.tenmo.model;

import java.util.Objects;

public class Transfer {

    private long transferId;
    private long transferTypeId;
    private long transferStatusId;
    private long accountFrom;
    private long accountTo;
    private double amount;
    private String userName;

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return transferId == transfer.transferId && transferTypeId == transfer.transferTypeId && transferStatusId == transfer.transferStatusId && accountFrom == transfer.accountFrom && accountTo == transfer.accountTo && Double.compare(transfer.amount, amount) == 0 && Objects.equals(userName, transfer.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount, userName);
    }
}

