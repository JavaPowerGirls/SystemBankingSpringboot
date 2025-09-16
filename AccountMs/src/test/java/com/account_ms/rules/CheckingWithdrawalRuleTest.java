package com.account_ms.rules;

import com.account_ms.exception.OverdraftLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingWithdrawalRuleTest {

    private CheckingWithdrawalRule checkingWithdrawalRule;

    // inicializar regla de checking
    @BeforeEach
    void setUp() {
        checkingWithdrawalRule = new CheckingWithdrawalRule();
    }

    // retiro valido dentro del overdraft
    @Test
    void validate_ValidWithdrawal_WithinOverdraftLimit() {
        assertDoesNotThrow(() -> checkingWithdrawalRule.validate(1000.0, 1400.0));
    }

    // retiro exactamente en limite overdraft
    @Test
    void validate_ValidWithdrawal_ExactlyAtOverdraftLimit() {
        assertDoesNotThrow(() -> checkingWithdrawalRule.validate(1000.0, 1500.0));
    }

    // retiro excede limite de overdraft
    @Test
    void validate_InvalidWithdrawal_ExceedsOverdraftLimit() {
        assertThrows(OverdraftLimitExceededException.class,
            () -> checkingWithdrawalRule.validate(1000.0, 1501.0));
    }

    // retiro excede overdraft desde balance cero
    @Test
    void validate_InvalidWithdrawal_ExceedsOverdraftLimitFromZero() {
        assertThrows(OverdraftLimitExceededException.class,
            () -> checkingWithdrawalRule.validate(0.0, 501.0));
    }
}