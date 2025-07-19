package repository;

import model.entities.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {
    private Map<String, Account> accounts = new HashMap<>();

    public void save(Account account){
        accounts.put(account.getAccountNumber(), account);
    }

    public Account findByNumber(String accountNumber){
         return  accounts.get(accountNumber);
    }

    public void update(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }
}
