package com.account_ms.services.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account_ms.repository.AccountRepository;
import com.account_ms.services.AccountService;

// Gestiona todas las operaciones bancarias relacionadas con cuentas
// Permite abrir cuentas, hacer depósitos, retiros y consultas de saldo
@Service 
@Transactional 
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository; 

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    } 

}
