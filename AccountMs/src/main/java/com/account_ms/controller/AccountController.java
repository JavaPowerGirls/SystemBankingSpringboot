package com.account_ms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 
 
}