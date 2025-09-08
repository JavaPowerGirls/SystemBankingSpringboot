package com.account_ms.dto;

import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountRequest {
    
    @Positive(message = "Amount must be positive")
    private double amount;

}
