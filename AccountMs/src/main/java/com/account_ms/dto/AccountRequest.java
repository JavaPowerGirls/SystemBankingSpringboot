package com.account_ms.dto;

import com.account_ms.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class AccountRequest {
    private Long clientId;
    private AccountType accountType;


}
