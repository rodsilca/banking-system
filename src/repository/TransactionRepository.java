package repository;

import model.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        return transactions.stream()
                .filter(a -> a.getAccount().getAccountNumber().equals(accountNumber))
                .toList();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
}
