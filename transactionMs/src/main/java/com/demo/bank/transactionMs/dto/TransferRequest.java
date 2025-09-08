package com.demo.bank.transactionMs.dto;
import lombok.Data;

@Data
public class TransferRequest {
    private Double amount;
    private String accountDestination;
    private String accountId;
}
