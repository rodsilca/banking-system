package service;

import model.entities.*;
import model.enums.TransactionType;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

import static application.Program.accounts;

public class AccountService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Account createAccount(int accountType, String password, Client client){
        Account newAccount;
        if (accountType == 1){
            newAccount = new CheckingAccount("1010", String.valueOf(accounts.size()+1),password,client,1000.0);
        } else if (accountType ==2 ) {
            newAccount = new SavingsAccount("2020", String.valueOf(accounts.size()+1),password,client);
        }else {
            throw new IllegalArgumentException("Invalid account type");
        }

        accountRepository.save(newAccount);

        return newAccount;
    }

    public void deposit(String accountNumber,double amount){
        Account account = accountRepository.findByNumber(accountNumber);
        if (account == null){
            throw  new IllegalArgumentException("Account not found: "+accountNumber);
        }

        account.deposit(amount);
        accountRepository.update(account);
        transactionRepository.addTransaction(new Transaction(LocalDateTime.now(),amount, TransactionType.DEPOSIT,account));
    }

    public void withdraw(String accountNumber,double amount){
        Account account = accountRepository.findByNumber(accountNumber);
        if (account == null){
            throw  new IllegalArgumentException("Account not found: "+accountNumber);
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.withdraw(amount);
        accountRepository.update(account);
        transactionRepository.addTransaction(new Transaction(LocalDateTime.now(),amount, TransactionType.WITHDRAW,account));
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByNumber(toAccountNumber);

        if (fromAccount==null || toAccount == null){
            throw new IllegalArgumentException("One or both accounts not found");
        }

        if (fromAccount.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient funds for account: "+ fromAccountNumber);
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.update(fromAccount);
        accountRepository.update(toAccount);
        transactionRepository.addTransaction(new Transaction(LocalDateTime.now(),amount, TransactionType.TRANSFER_SENT,fromAccount));
        transactionRepository.addTransaction(new Transaction(LocalDateTime.now(),amount, TransactionType.TRANSFER_RECEIVED,toAccount));
    }

    public List<Transaction> transactionLogs(String accountNumber) {
        return transactionRepository.getTransactionsForAccount(accountNumber);
    }

    public Account findAccount(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }
}
