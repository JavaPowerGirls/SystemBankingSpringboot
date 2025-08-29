package com.account_ms.services.impl;
import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account_ms.repository.AccountRepository;
import com.account_ms.services.AccountService;
import java.util.List;

// Gestiona todas las operaciones bancarias relacionadas con cuentas
// Permite abrir cuentas, hacer depósitos, retiros y consultas de saldo
@Service 
@Transactional 
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BankAccount createAccount(Long clientId, AccountType type) {
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
        accountRepository.deleteById(id);
    }

}

