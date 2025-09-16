package com.account_ms.rules;

import com.account_ms.model.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalRuleFactoryTest {

    @Mock
    private SavingsWithdrawalRule savingsWithdrawalRule;

    @Mock
    private CheckingWithdrawalRule checkingWithdrawalRule;

    private WithdrawalRuleFactory withdrawalRuleFactory;

    // configurar factory para tests
    @BeforeEach
    void setUp() {
        withdrawalRuleFactory = new WithdrawalRuleFactory(savingsWithdrawalRule, checkingWithdrawalRule);
    }

    // obtener regla para cuenta savings
    @Test
    void getRule_SavingsAccount() {
        WithdrawalRule result = withdrawalRuleFactory.getRule(AccountType.SAVINGS);
        assertEquals(savingsWithdrawalRule, result);
    }

    // obtener regla para cuenta checking
    @Test
    void getRule_CheckingAccount() {
        WithdrawalRule result = withdrawalRuleFactory.getRule(AccountType.CHECKING);
        assertEquals(checkingWithdrawalRule, result);
    }

    // tipo de cuenta no soportado
    @Test
    void getRule_UnsupportedAccountType() {
        assertThrows(IllegalArgumentException.class, () -> withdrawalRuleFactory.getRule(null));
    }
}