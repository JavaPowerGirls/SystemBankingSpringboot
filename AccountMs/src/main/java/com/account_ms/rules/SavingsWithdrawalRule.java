package com.account_ms.rules;

import com.account_ms.exception.InsufficientFundsException;
import org.springframework.stereotype.Component;

@Component
public class SavingsWithdrawalRule implements WithdrawalRule  {
    @Override
    public void validate(double currentBalance, double amount) {
        if (currentBalance - amount < 0) {
            throw new InsufficientFundsException("Savings accounts cannot have negative balance");
        }
    }
}
