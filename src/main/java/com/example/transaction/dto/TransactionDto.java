package com.example.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionDto {

private String accountFrom;
private String accountTo;
private Double amount;
private String type;

}
