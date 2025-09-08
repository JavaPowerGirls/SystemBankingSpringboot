package com.banking.transactions.model;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Document("transactions")
public class Transaction {

    @Id
    private String id;

    private TransactionType type;

    private Double amount;

    private LocalDate date;

    private String sourceAccountId;

    private String destinationAccountId;

}
