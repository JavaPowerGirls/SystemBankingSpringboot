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
        BankAccount account = accountService.createAccount(request.getClientId(), request.getAccountType());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
    
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable Long id) {
        BankAccount account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}/depositar")
    public ResponseEntity<BankAccount> deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest amountRequest) {
        BankAccount account = accountService.deposit(id, amountRequest.getAmount());
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}/retirar")
    public ResponseEntity<BankAccount> withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequest amountRequest) {
        BankAccount account = accountService.withdraw(id, amountRequest.getAmount());
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