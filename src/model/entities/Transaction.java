package model.entities;

import model.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private double value;
    private TransactionType transactionType;
    private Account account;

    public Transaction(LocalDateTime date, double value, TransactionType transactionType,Account account) {
        this.date = date;
        this.value = value;
        this.transactionType = transactionType;
        this.account = account;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", value=" + value +
                ", transactionType=" + transactionType +
                '}';
    }
}
