package com.banking.transactions.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "transactions")
@Builder
public class Transaction {

    @Id
    private String id;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private Double amount;
    private LocalDate date;
    private TransactionType type;

}

