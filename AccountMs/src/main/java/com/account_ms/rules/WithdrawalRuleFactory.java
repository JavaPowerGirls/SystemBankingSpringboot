package com.account_ms.rules;

import org.springframework.stereotype.Component;

@Component
public class WithdrawalRuleFactory {

    private WithdrawalRule rule;

    public void setWithdrawalRule(WithdrawalRule rule) {
        this.rule = rule;
    }

    public void validateWithdrawal(double currentBalance, double amount) {
        if (rule == null) {
            throw new IllegalStateException("Withdrawal rule not set");
        }
        rule.validate(currentBalance, amount);
    }
}
