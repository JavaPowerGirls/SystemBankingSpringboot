package com.account_ms.rules;

import com.account_ms.model.AccountType;
import org.springframework.stereotype.Component;

@Component
public class WithdrawalRuleFactory {

    private final SavingsWithdrawalRule savingsWithdrawalRule;
    private final CheckingWithdrawalRule checkingWithdrawalRule;

    public WithdrawalRuleFactory(SavingsWithdrawalRule savingsWithdrawalRule,
                                CheckingWithdrawalRule checkingWithdrawalRule) {
        this.savingsWithdrawalRule = savingsWithdrawalRule;
        this.checkingWithdrawalRule = checkingWithdrawalRule;
    }

    public WithdrawalRule getRule(AccountType accountType) {
        if (accountType == AccountType.SAVINGS) {
            return savingsWithdrawalRule;
        } else if (accountType == AccountType.CHECKING) {
            return checkingWithdrawalRule;
        } else {
            throw new IllegalArgumentException("Unsupported account type: " + accountType);
        }
    }
}
