package com.banking.transactions.dto;

import java.time.LocalDate;

import com.banking.transactions.model.TransactionType;
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
    private String sourceAccountId;
    private String destinationAccountId; // Solo se incluye para TRANSFER
}
