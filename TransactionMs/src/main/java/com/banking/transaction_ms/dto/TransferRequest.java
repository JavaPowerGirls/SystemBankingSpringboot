package com.banking.transaction_ms.dto;

import lombok.Data;


@Data
public class TransferRequest {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
}