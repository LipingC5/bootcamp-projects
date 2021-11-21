package com.techelevator;

public class Account {

    private double accountBalance;

    public Account() {
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addMoneyToAccountBalance(double moneyAddedByUser) {
        if (accountBalance + moneyAddedByUser <= 4500) {
            accountBalance += moneyAddedByUser;
        }
    }

    public void subtractMoneyFromAccountBalance(double productPrice, int quantity) {
        if (accountBalance > (productPrice * quantity)) {
            accountBalance -= productPrice * quantity;
        }
    }
}
