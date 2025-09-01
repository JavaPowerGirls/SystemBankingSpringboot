package com.account_ms.model;

import lombok.*;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Representa una cuenta bancaria en el sistema
// Una cuenta bancaria pertenece a un cliente y puede ser de tipo AHORROS o CORRIENTE
@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;// Referencia al Enum

    @Column(name = "client_id", nullable = false)
    private Long clientId; // referencia al cliente

    // Constructor por defecto para JPA/Hibernate


    public BankAccount(Long clientId, AccountType accountType) {
        this.clientId = clientId;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.accountType = accountType;
    }

    // Genera un número de cuenta único
    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Metodo para deposito dinero de la cuenta bancaria
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    // Metodo para retirar dinero de la cuenta bancaria
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        double newBalance = this.balance - amount;
        checkWithdrawal(newBalance);
        // todas las validaciones, se actualiza el saldo
        this.balance = newBalance;
    }

    // Metodo para verificar si el retiro es valido
    private void checkWithdrawal(double newBalance) {
        // Restricción para cuentas de ahorro: el saldo nunca puede ser negativo
        if (accountType == AccountType.SAVINGS) {
            if (newBalance < 0) {
                throw new IllegalArgumentException("Savings accounts cannot have a negative balance.");
            }
        } else if (accountType == AccountType.CHECKING) {
            // Restricción para cuentas corrientes: pueden tener sobregiro hasta un límite
            double overdraftLimit = this.accountType.getOverdraftLimit();
            if (newBalance < overdraftLimit) {
                throw new IllegalArgumentException("Checking accounts cannot exceed overdraft limit of " + overdraftLimit);
            }
        }
    }
}