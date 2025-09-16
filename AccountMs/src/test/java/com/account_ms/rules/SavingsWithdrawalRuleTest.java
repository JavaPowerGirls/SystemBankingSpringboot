package com.account_ms.rules;

import com.account_ms.exception.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SavingsWithdrawalRuleTest {

    private SavingsWithdrawalRule savingsWithdrawalRule;

    // setup regla savings
    @BeforeEach
    void setUp() {
        savingsWithdrawalRule = new SavingsWithdrawalRule();
    }

    // retiro valido con fondos suficientes
    @Test
    void validate_ValidWithdrawal_SufficientFunds() {
        assertDoesNotThrow(() -> savingsWithdrawalRule.validate(1000.0, 500.0));
    }

    // retiro por balance exacto
    @Test
    void validate_ValidWithdrawal_ExactBalance() {
        assertDoesNotThrow(() -> savingsWithdrawalRule.validate(1000.0, 1000.0));
    }

    // retiro falla por fondos insuficientes
    @Test
    void validate_InvalidWithdrawal_InsufficientFunds() {
        assertThrows(InsufficientFundsException.class,
            () -> savingsWithdrawalRule.validate(1000.0, 1001.0));
    }

    // retiro desde balance cero
    @Test
    void validate_InvalidWithdrawal_ZeroBalance() {
        assertThrows(InsufficientFundsException.class,
            () -> savingsWithdrawalRule.validate(0.0, 1.0));
    }
}