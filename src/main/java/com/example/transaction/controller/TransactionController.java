package com.example.transaction.controller;

import com.example.transaction.dto.TransactionDto;
import com.example.transaction.dto.TransferRequest;
import com.example.transaction.dto.WithdrawRequest;
import com.example.transaction.model.Transaction;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction.services.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@RestController
@RequestMapping("/transaction")


public class TransactionController {

    private final TransactionService transactionService;
    //private TransactionController service;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Deposito
    @PostMapping("/deposit")
    public Mono<Transaction> deposit(@RequestBody TransactionDto request) {
     return transactionService.deposit(request);
    }
    // Retirar
    @PostMapping("/withdraw")
    public Mono<Transaction> withdraw(@RequestBody WithdrawRequest request) {
        return transactionService.withdraw(request);
    }

    // Transferencia
    @PostMapping("/transfer")
    public Mono<Transaction> transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }

    @GetMapping("/history")
    public Flux<Transaction> history(@RequestBody TransferRequest request) {
        return transactionService.history(request);
    }
}















