package model.entities;

import model.enums.TransactionType;

import java.time.LocalDateTime;

public class CheckingAccount extends Account{
    private Double lineOfCredit;

    public CheckingAccount(String agency, String accountNumber,String password, Client client, Double lineOfCredit) {
        super(agency, accountNumber,password,client);
        this.lineOfCredit = lineOfCredit;
    }

    public Double getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(Double lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
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
