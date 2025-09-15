package com.account_ms.dto;

import com.account_ms.model.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountRequest {
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Account type is required")
    private AccountType accountType;
}
