package model.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  abstract class Account  implements Serializable {
    private String agency;
    private String accountNumber;
    private Double balance;
    private String password;

    private Client client;
    private List<Transaction> transactionList = new ArrayList<>();

    public Account() {
    }

    public Account(String agency, String accountNumber,String password, Client client) {
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.password = password;
        this.client = client;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void getTransactionList() {
        transactionList.forEach(System.out::println);
    }

    public void addTransactionList(Transaction transaction) {
        transactionList.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountNumber);
    }

    @Override
    public String toString() {
        return "Agency: "+agency+ "Number: " +accountNumber+ " Holder: "+client.getName();
    }

    public abstract void deposit(double value);
    public abstract void withdraw(double value);
    public abstract void transfer(double value, Account destinyAccount);
    public abstract void receiveTransfer(Transaction transaction);

}
