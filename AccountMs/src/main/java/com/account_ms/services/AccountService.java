package com.account_ms.services;
import com.account_ms.dto.AccountRequest;


import com.account_ms.dto.AmountRequest;
import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;

import java.util.List;

// Define las operaciones bancarias disponibles
public interface AccountService {
    BankAccount createAccount(AccountRequest request);
    List<BankAccount> getAllAccounts();
    BankAccount getAccountById(Long id);
    BankAccount deposit(Long id, AmountRequest request);
    BankAccount withdraw(Long id, AmountRequest request);
    void deleteAccount(Long id);
    List<BankAccount> getAccountsByClientId(Long clientId);

    
}
