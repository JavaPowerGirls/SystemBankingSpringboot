package com.account_ms.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    // probar constructor con parametros
    @Test
    void constructor_WithClientIdAndAccountType() {
        BankAccount account = new BankAccount(1L, AccountType.CHECKING);

        assertEquals(1L, account.getClientId());
        assertEquals(AccountType.CHECKING, account.getAccountType());
        assertEquals(0.0, account.getBalance());
        assertNotNull(account.getAccountNumber());
        assertTrue(account.getAccountNumber().startsWith("ACC-"));
        assertEquals(12, account.getAccountNumber().length());
    }

    // deposito suma al balance
    @Test
    void deposit_AddsAmountToBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100.0);

        account.deposit(50.0);

        assertEquals(150.0, account.getBalance());
    }

    // deposito desde balance cero
    @Test
    void deposit_FromZeroBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(0.0);

        account.deposit(100.0);

        assertEquals(100.0, account.getBalance());
    }

    // retiro resta del balance
    @Test
    void withdraw_SubtractsAmountFromBalance() {
        BankAccount account = new BankAccount();
        account.setBalance(100.0);

        account.withdraw(30.0);

        assertEquals(70.0, account.getBalance());
    }

    // retiro puede dejar balance negativo
    @Test
    void withdraw_CanGoNegative() {
        BankAccount account = new BankAccount();
        account.setBalance(50.0);

        account.withdraw(100.0);

        assertEquals(-50.0, account.getBalance());
    }

    // generar numeros de cuenta unicos
    @Test
    void generateAccountNumber_CreatesUniqueNumbers() {
        BankAccount account1 = new BankAccount(1L, AccountType.CHECKING);
        BankAccount account2 = new BankAccount(2L, AccountType.SAVINGS);

        assertNotEquals(account1.getAccountNumber(), account2.getAccountNumber());
        assertTrue(account1.getAccountNumber().matches("ACC-[A-Z0-9]{8}"));
        assertTrue(account2.getAccountNumber().matches("ACC-[A-Z0-9]{8}"));
    }
}