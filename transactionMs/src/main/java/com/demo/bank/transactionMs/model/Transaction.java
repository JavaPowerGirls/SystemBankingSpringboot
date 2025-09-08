package com.demo.bank.transactionMs.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
@Builder
public class Transaction {

    @Id
    private String id;
    private String accountOrigin;
    private String accountDestination;
    private Double amount;
    private LocalDate date;
    private TransactionType type;

}

