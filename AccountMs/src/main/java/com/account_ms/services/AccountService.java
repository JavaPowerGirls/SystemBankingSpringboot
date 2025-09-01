package com.account_ms.services;
import com.account_ms.dto.AccountRequest;


import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;

import java.util.List;

// Define las operaciones bancarias disponibles
public interface AccountService {
    BankAccount createAccount(Long clientId, AccountType type);
    List<BankAccount> getAllAccounts();
    BankAccount getAccountById(Long id);
    BankAccount deposit(Long id, double amount);
    BankAccount withdraw(Long id, double amount);
    void deleteAccount(Long id);
    List<BankAccount> getAccountsByClientId(Long clientId);

    
}
