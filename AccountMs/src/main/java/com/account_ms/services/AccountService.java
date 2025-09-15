package com.account_ms.services;
import com.account_ms.dto.AccountRequest;


import com.account_ms.dto.AmountRequest;
import com.account_ms.model.BankAccount;

import java.util.List;

// Define las operaciones bancarias disponibles
public interface AccountService {
    BankAccount createAccount(AccountRequest request);
    List<BankAccount> getAllAccounts();
    BankAccount getAccountByAccountNumber(String accountNumber);
    BankAccount deposit(String accountNumber, AmountRequest request);
    BankAccount withdraw(String accountNumber, AmountRequest request);
    void deleteAccount(Long id);
    List<BankAccount> getAccountsByClientId(Long clientId);
    
}
