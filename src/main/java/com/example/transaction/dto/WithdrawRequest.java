package com.example.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WithdrawRequest {

        private String accountFrom;
        private String accountTo;
        private Double amount;
        private LocalDateTime date;

}