package com.bankingsystem.domain.services;

import java.util.List;

import com.bankingsystem.domain.model.BankAccount;

// Define las operaciones bancarias disponibles 
public interface AccountService {

    // Permite abrir una nueva cuenta bancaria
    BankAccount openAccount(Long clientId, String accountType);

    // Permite depositar dinero en una cuenta
    void depositToAccount(String accountNumber, Double amount);
  
    // Permite retirar dinero de una cuenta
    void withdrawFromAccount(String accountNumber, Double amount);
    
    // Consulta el saldo actual disponible en una cuenta
    Double getAccountBalance(String accountNumber);
    
    // Obtiene todas las cuentas que pertenecen a un cliente
    List<BankAccount> getAccountsByClientId(Long clientId);
    
}
