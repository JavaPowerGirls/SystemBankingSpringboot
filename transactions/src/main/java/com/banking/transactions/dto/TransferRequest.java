package com.banking.transactions.dto;

import lombok.Data;


@Data
public class TransferRequest {
    private String sourceAccountId;
    private String destinationAccountId;
    private Double amount;
}
