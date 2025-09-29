package com.account_ms.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalRuleFactoryTest {

    @Mock
    private WithdrawalRule withdrawalRule;

    private WithdrawalRuleFactory withdrawalRuleFactory;

    // configurar factory para tests
    @BeforeEach
    void setUp() {
        withdrawalRuleFactory = new WithdrawalRuleFactory();
    }

    // validar retiro exitoso
    @Test
    void validateWithdrawal_Success() {
        withdrawalRuleFactory.setWithdrawalRule(withdrawalRule);
        doNothing().when(withdrawalRule).validate(1000.0, 100.0);

        assertDoesNotThrow(() -> withdrawalRuleFactory.validateWithdrawal(1000.0, 100.0));
        verify(withdrawalRule).validate(1000.0, 100.0);
    }

    // validar retiro sin regla configurada
    @Test
    void validateWithdrawal_NoRuleSet() {
        assertThrows(IllegalStateException.class, () -> withdrawalRuleFactory.validateWithdrawal(1000.0, 100.0));
    }

    // configurar regla de retiro
    @Test
    void setWithdrawalRule() {
        withdrawalRuleFactory.setWithdrawalRule(withdrawalRule);
        assertDoesNotThrow(() -> withdrawalRuleFactory.validateWithdrawal(1000.0, 100.0));
    }
}