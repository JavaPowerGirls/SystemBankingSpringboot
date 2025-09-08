package com.example.transaction.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
        private String accountFrom;
        private String accountTo;
        private Double amount;
    }
