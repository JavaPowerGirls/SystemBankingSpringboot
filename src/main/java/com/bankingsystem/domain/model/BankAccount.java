package com.bankingsystem.domain.model;

// Constructor: todos los atributos
public class BankAccount {
    private int accountId;
    private String accountNumber;
    private double balance;
    private AccountType accountType;// Enum reference
    private double OVERDRAFT_LIMIT;

    public BankAccount(int accountId, String accountNumber, double balance, AccountType accountType, double OVERDRAFT_LIMIT) {
    this.accountId = accountId;
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.accountType = accountType;
    }
    // Metodo para deposito dinero de la cuenta bancaria
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    // Metodo para retirar dinero de la cuenta bancaria
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive.");
        }

// Restricción para cuentas de ahorro: el saldo nunca puede ser negativo
        if (accountType == AccountType.SAVINGS) {
            if (newBalance < 0) {
                throw new IllegalStateException("Savings accounts cannot have a negative balance.");
            }

            // Restricción para cuentas corrientes: pueden tener sobregiro hasta un límite
        } else if (accountType == AccountType.CHECKING) {
            if (newBalance < OVERDRAFT_LIMIT) {
                throw new IllegalStateException("Checking accounts cannot exceed overdraft limit of " + OVERDRAFT_LIMIT);
            }
        }
       // todas las validaciones, se actualiza el saldo
        balance = newBalance;
    }
    public double getBalance() {
        return balance;
    }

    private double amount;
    double newBalance = balance - amount;

    // Devuelve el tipo de cuenta (SAVINGS o CHECKING)
    public AccountType getAccountType() {
        return accountType;
    }
    // Permite cambiar el tipo de cuenta
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", OVERDRAFT_LIMIT=" + OVERDRAFT_LIMIT + '\'' +
                '}';
    }
}
