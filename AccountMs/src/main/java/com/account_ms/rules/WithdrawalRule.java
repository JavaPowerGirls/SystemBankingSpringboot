package com.account_ms.rules;

public interface WithdrawalRule {
    void validate(double currentBalance, double amount);
}


