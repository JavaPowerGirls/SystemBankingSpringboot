package com.account_ms.rules;

import com.account_ms.model.AccountType;

public interface WithdrawalRule {
    void validate(double currentBalance, double amount);
}


