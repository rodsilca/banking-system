package model.entities;

public class SavingsAccount extends Account{

    public SavingsAccount(String agency, String accountNumber,String password, Client client) {
        super(agency, accountNumber, password, client);
    }

    @Override
    public void deposit(double value) {
        setBalance(getBalance() + value);
    }

    @Override
    public void withdraw(double value) {
        if (value > getBalance()){
            System.out.println("Insufficient Funds");
        }else {
            setBalance(getBalance() - value);

        }

    }

    @Override
    public void transfer(double value, Account destinyAccount) {
        if (value > getBalance()){
            System.out.println("Insufficient funds");
        }else {
            double transferFee = value * 0.02;
            setBalance(getBalance() - (value + transferFee));
        }
    }

    @Override
    public void receiveTransfer(Transaction transaction) {
        double value = transaction.getValue();
        setBalance(getBalance() + value);

    }
}
