package com.bankingsystem.interfaces.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.domain.model.BankAccount;
import com.bankingsystem.domain.services.AccountService;
import com.bankingsystem.interfaces.rest.dto.BankAccountRequestDTO;
import com.bankingsystem.interfaces.rest.dto.BankAccountResponseDTO;
import com.bankingsystem.interfaces.rest.mappers.BankAccountMapper;

// API REST para gestionar las cuentas bancarias
@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final AccountService accountService;
    private final BankAccountMapper mapper;

    public BankAccountController(AccountService accountService, BankAccountMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }

    // Permite que un cliente abra una nueva cuenta bancaria
    @PostMapping("/open")
    public ResponseEntity<?> openAccount(@RequestBody BankAccountRequestDTO requestDTO) {
        try {
            BankAccount account = accountService.openAccount(requestDTO.clientId, requestDTO.accountType);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(account));
        } catch (IllegalArgumentException e) { // errores de negocio
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) { // errores de sistema
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error"); 
        }
    }

    // Permite hacer depósitos de dinero a una cuenta bancaria
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam Double amount) {
        try {
            accountService.depositToAccount(accountNumber, amount);
            return ResponseEntity.ok("Deposit successful");
        } catch (IllegalArgumentException e) { // errores de negocio
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) { // errores de sistema
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Permite retirar dinero de una cuenta bancaria
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam Double amount) {
        try {
            accountService.withdrawFromAccount(accountNumber, amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Consulta el saldo disponible en una cuenta bancaria
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String accountNumber) {
        try {
            Double balance = accountService.getAccountBalance(accountNumber);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    // Muestra todas las cuentas bancarias que tiene un cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAccountsByClientId(@PathVariable Long clientId) {
        try {
            List<BankAccountResponseDTO> accounts = accountService.getAccountsByClientId(clientId)
                .stream() // Convierte en stream
                .map(account -> mapper.toDto(account))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(accounts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}