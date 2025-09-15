package com.banking.transaction_ms.mapper;

import com.banking.transaction_ms.dto.TransactionResponse;
import com.banking.transaction_ms.model.Transaction;

public final class TransactionMapper {

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .sourceAccountNumber(transaction.getSourceAccountNumber())
                .destinationAccountNumber(transaction.getDestinationAccountNumber())
                // Será null para DEPOSIT/WITHDRAWAL
                .build();
    }

}