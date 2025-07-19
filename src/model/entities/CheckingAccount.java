package model.entities;

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

        addTransactionList(transaction);
    }
}
