package model.entities;

import model.enums.TransactionType;

import java.time.LocalDateTime;

public class SavingsAccount extends Account{

    public SavingsAccount(String agency, String accountNumber,String password, Client client) {
        super(agency, accountNumber, password, client);
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
            double transferFee = value * 0.02;
            setBalance(getBalance() - (value + transferFee));

            addTransactionList(new Transaction(LocalDateTime.now(),value, TransactionType.TRANSFER_SENT));

            destinyAccount.receiveTransfer(new Transaction(LocalDateTime.now(),value, TransactionType.TRANSFER_RECEIVED));
        }
    }

    @Override
    public void receiveTransfer(Transaction transaction) {
        double value = transaction.getValue();
        setBalance(getBalance() + value);

        addTransactionList(transaction);
    }
}
