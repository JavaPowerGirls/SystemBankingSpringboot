package com.account_ms.controller;
import java.util.List;
import com.account_ms.dto.AmountRequest;

import com.account_ms.dto.AccountRequest;
import com.account_ms.model.BankAccount;
import org.springframework.web.bind.annotation.*;

import com.account_ms.services.AccountService;

// API REST para gestionar las cuentas bancarias
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @SuppressWarnings("unused")
    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }
        @PostMapping
        public BankAccount createAccount (@RequestBody AccountRequest request) {
            return accountService.createAccount(request.getClientId(), request.getAccountType());

        }
        @GetMapping
        public List<BankAccount> getAllAccounts() {
                return accountService.getAllAccounts();


        }
        @GetMapping("/{id}")
        public BankAccount getAccountById(@PathVariable Long id) {
               return accountService.getAccountById(id);
        }

        @PutMapping("/{id}/depositar")
        public BankAccount deposit(@PathVariable Long id, @RequestBody AmountRequest amountRequest) {
            return accountService.deposit(id, amountRequest.getAmount());
        }

        @PutMapping("/{id}/retirar")
        public BankAccount withdraw(@PathVariable Long id, @RequestBody AmountRequest amountRequest) {
              return accountService.withdraw(id, amountRequest.getAmount());

        }
        @DeleteMapping("/{id}")
        public void deleteAccount(@PathVariable Long id ) {
            accountService.deleteAccount(id);
        }


}