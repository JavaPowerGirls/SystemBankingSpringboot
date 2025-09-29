package com.account_ms.rules;

import com.account_ms.exception.OverdraftLimitExceededException;
import com.account_ms.model.AccountType;
import org.springframework.stereotype.Component;

@Component
public class CheckingWithdrawalRule implements WithdrawalRule{
    @Override
    public void validate(double currentBalance, double amount) {
        if (currentBalance - amount < -500) {
            throw new OverdraftLimitExceededException("Checking accounts cannot exceed overdraft limit of -500");
        }
    }

}
