package com.account_ms.services.impl;

import com.account_ms.client.CustomerClient;
import com.account_ms.dto.AccountRequest;
import com.account_ms.dto.AmountRequest;
import com.account_ms.model.BankAccount;
import com.account_ms.rules.WithdrawalRule;
import com.account_ms.rules.WithdrawalRuleFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account_ms.repository.AccountRepository;
import com.account_ms.services.AccountService;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;
    private final WithdrawalRuleFactory withdrawalRuleFactory;

    @Value("${customer.service.url:http://localhost:8081}")
    private String customerServiceUrl;

    public AccountServiceImpl(AccountRepository accountRepository,
                            CustomerClient customerClient,
                            WithdrawalRuleFactory withdrawalRuleFactory) {
        this.accountRepository = accountRepository;
        this.customerClient = customerClient;
        this.withdrawalRuleFactory = withdrawalRuleFactory;
    }

    @Override
    public BankAccount createAccount(AccountRequest request) {
        
        // Validar que el cliente existe antes de crear la cuenta
        if(customerClient.existsById(request.getClientId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID " + request.getClientId() + " does not exist");
        }
        BankAccount account = new BankAccount(request.getClientId(), request.getAccountType());
        return accountRepository.save(account);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public BankAccount getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with account number: " + accountNumber));
    }

    @Override
    public BankAccount deposit(String accountNumber, AmountRequest request) {
        BankAccount account = getAccountByAccountNumber(accountNumber);
        account.deposit(request.getAmount());
        return accountRepository.save(account);

    }

    @Override
    public BankAccount withdraw(String accountNumber, AmountRequest request) {
        BankAccount account = getAccountByAccountNumber(accountNumber);

        // Aplicar reglas de retiro específicas según el tipo de cuenta
        WithdrawalRule rule = withdrawalRuleFactory.getRule(account.getAccountType());
        rule.validate(account.getBalance(), request.getAmount());

        account.withdraw(request.getAmount());
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")); 
        accountRepository.deleteById(id);
    }

    @Override
    public List<BankAccount> getAccountsByClientId(Long clientId) {
        if(customerClient.existsById(clientId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client does not exist");
        }
        return accountRepository.findByClientId(clientId);
    }
}
