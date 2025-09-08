package com.account_ms.controller;
import java.util.List;
import com.account_ms.dto.AmountRequest;

import com.account_ms.dto.AccountRequest;
import com.account_ms.model.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.account_ms.services.AccountService;

import javax.validation.Valid;

// API REST para gestionar las cuentas bancarias
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public ResponseEntity<BankAccount> createAccount(@Valid @RequestBody AccountRequest request) {
        BankAccount account = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
    
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccountByAccountNumber(@PathVariable String accountNumber) {
        BankAccount account = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<BankAccount> deposit(@PathVariable String accountNumber, @Valid @RequestBody AmountRequest request) {
        BankAccount account = accountService.deposit(accountNumber, request);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{accountNumber}/withdrawal")
    public ResponseEntity<BankAccount> withdraw(@PathVariable String accountNumber, @Valid @RequestBody AmountRequest request) {
        BankAccount account = accountService.withdraw(accountNumber, request);
        return ResponseEntity.ok(account);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para que CustomerMs pueda consultar cuentas por clientId
    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<BankAccount>> getAccountsByClientId(@PathVariable Long clientId) {
        List<BankAccount> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
    }
}