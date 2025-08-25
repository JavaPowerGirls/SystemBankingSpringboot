package com.bankingsystem.application.services;

import com.bankingsystem.domain.services.AccountService;
import com.bankingsystem.infrastructure.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

  //  o	Al abrir una cuenta bancaria, se debe verificar que el cliente existe.
    //o	El número de cuenta debe ser único y generado automáticamente por el sistema (podría ser una secuencia o un identificador aleatorio).
    //o	El tipo de cuenta debe ser Ahorros o Corriente.
    //Un cliente puede abrir una cuenta bancaria, especificando el tipo de cuenta (Ahorros o Corriente).

    @Override
    public void openAccount(String accountType, Long) {
        if()



    }

    @Override
    public void depositAccount(String command) {

    }

    @Override
    public void withdrawAccount(String command) {

    }
}
