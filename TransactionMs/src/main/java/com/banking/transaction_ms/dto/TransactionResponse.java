package com.banking.transaction_ms.dto;

import java.time.LocalDate;

import com.banking.transaction_ms.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String id;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
    private String sourceAccountNumber;
    private String destinationAccountNumber; // Solo se incluye para TRANSFER
}