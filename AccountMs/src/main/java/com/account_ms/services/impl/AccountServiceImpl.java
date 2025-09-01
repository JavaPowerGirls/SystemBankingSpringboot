package com.account_ms.services.impl;

import com.account_ms.client.CustomerServiceClient;
import com.account_ms.dto.AccountRequest;
import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account_ms.repository.AccountRepository;
import com.account_ms.services.AccountService;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerServiceClient customerServiceClient;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerServiceClient customerServiceClient) {
        this.accountRepository = accountRepository;
        this.customerServiceClient = customerServiceClient;
    }

    @Override
    public BankAccount createAccount(Long clientId, AccountType type) {
        
        // Validar que el cliente existe antes de crear la cuenta
        if (!customerServiceClient.clientExists(clientId)) {
            throw new IllegalArgumentException("Cannot create account: Client with ID " + clientId + " not found");
        }
        
        BankAccount account = new BankAccount(clientId, type);
        return accountRepository.save(account);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public BankAccount getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }

    @Override
    public BankAccount deposit(Long id, double amount) {
        BankAccount account = getAccountById(id);
        account.deposit(amount);
        return accountRepository.save(account);

    }

    @Override
    public BankAccount withdraw(Long id, double amount) {
        BankAccount account = getAccountById(id);
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        getAccountById(id); 
        accountRepository.deleteById(id);
    }

    @Override
    public List<BankAccount> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
