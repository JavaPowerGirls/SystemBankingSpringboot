package com.example.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")

    public class Transaction {

    @Id
    private String id;
    private TransactionType Type;
    private String accountFrom;
    private String accountTo;
    private Double amount;
    private LocalDateTime date;
}
