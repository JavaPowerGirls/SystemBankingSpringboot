package com.bankingsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingsystem.domain.model.AccountType;
import com.bankingsystem.domain.model.BankAccount;
import com.bankingsystem.domain.services.AccountService;
import com.bankingsystem.infrastructure.AccountRepository;
import com.bankingsystem.infrastructure.ClientRepository;

// Gestiona todas las operaciones bancarias relacionadas con cuentas
// Permite abrir cuentas, hacer depósitos, retiros y consultas de saldo
@Service 
@Transactional 
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository; 
    private final ClientRepository clientRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    // Permite a un cliente abrir una nueva cuenta bancaria (AHORROS o CORRIENTE)
    @Override
    public BankAccount openAccount(Long clientId, String accountType) {

        //verifica si existe el client Id
        if (!clientRepository.existsById(clientId)) {
            throw new IllegalArgumentException("Client with ID " + clientId + " not found");
        }


        // Verifica si el tipo de cuenta es válido
        AccountType type;
        try {
            type = AccountType.valueOf(accountType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account type: " + accountType
                    + ". Valid types are: SAVINGS, CHECKING");
        }

        // Crea una nueva cuenta bancaria
        BankAccount newAccount = new BankAccount(clientId, type); 

        try {
            // Guarda la nueva cuenta bancaria
            return accountRepository.save(newAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error creating bank account: " + e.getMessage(), e);
        }
    }

    // Permite hacer depósitos de dinero a una cuenta bancaria
    @Override
    public void depositToAccount(String accountNumber, Double amount) {
        // Busca la cuenta bancaria por su número de cuenta
        BankAccount account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found"));

        // Verifica si el monto del depósito es válido
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        try {
            // Realiza el depósito de dinero en la cuenta bancaria
            account.deposit(amount);
            // Guarda la cuenta bancaria actualizada
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error depositing to account: " + e.getMessage(), e);
        }
    }

    // Permite retirar dinero de una cuenta bancaria respetando las reglas de sobregiro
    @Override
    public void withdrawFromAccount(String accountNumber, Double amount) {
        // Busca la cuenta bancaria por su número de cuenta
        BankAccount account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found"));

        // Verifica si el monto del retiro es válido
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        try {
            // Realiza el retiro de dinero en la cuenta bancaria
            account.withdraw(amount);
            // Guarda la cuenta bancaria actualizada
            accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error withdrawing from account: " + e.getMessage(), e);
        }
    }

    // Consulta el saldo actual de una cuenta bancaria
    @Override
    public Double getAccountBalance(String accountNumber) {
        // Busca la cuenta bancaria por su número de cuenta
        return accountRepository.findByAccountNumber(accountNumber)
                .map(BankAccount::getBalance) // Obtiene el saldo de la cuenta bancaria
                .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found")); 
                // Si la cuenta no existe, lanza una excepción
    }
    
    // Obtiene todas las cuentas bancarias que pertenecen a un cliente específico
    @Override
    public List<BankAccount> getAccountsByClientId(Long clientId) {
        // Verifica si el cliente existe
        if (!clientRepository.existsById(clientId)) {
            throw new IllegalArgumentException("Client with ID " + clientId + " does not exist");
        }
        
        // Obtiene todas las cuentas bancarias
        return accountRepository.findAll()
            .stream() // Convierte la lista de cuentas bancarias en un stream
            .filter(account -> clientId.equals(account.getClientId())) // Filtra las cuentas bancarias por el cliente
            .collect(Collectors.toList()); // Convierte el stream a una lista
    }
    
     

}
