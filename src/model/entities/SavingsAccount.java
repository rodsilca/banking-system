package model.entities;

import model.enums.TransactionType;

import java.time.LocalDateTime;

public class SavingsAccount extends Account{

    public SavingsAccount(String agency, String accountNumber, Double balance, Client client) {
        super(agency, accountNumber, balance, client);
    }

    @Override
    public void deposit(double value) {
        setBalance(getBalance() + value);
        addTransactionList(new Transaction(LocalDateTime.now(),value, TransactionType.DEPOSIT));
    }

    @Override
    public void withdraw(double value) {
        if (value > getBalance()){
            System.out.println("Insufficient Funds");
        }else {
            setBalance(getBalance() - value);
            addTransactionList(new Transaction(LocalDateTime.now(),value, TransactionType.WITHDRAW));
        }

    }

    @Override
    public void transfer(double value, Account destinyAccount) {
        if (value > getBalance()){
            System.out.println("Insufficient funds");
        }else {
            destinyAccount.setBalance(getBalance() + value);
            addTransactionList(new Transaction(LocalDateTime.now(),value, TransactionType.TRANSFER_SENT));
        }
    }
}
