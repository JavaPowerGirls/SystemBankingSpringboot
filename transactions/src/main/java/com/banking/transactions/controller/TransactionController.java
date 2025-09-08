package com.banking.transactions.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;

import com.banking.transactions.dto.AmountRequest;
import com.banking.transactions.dto.TransactionResponse;
import com.banking.transactions.dto.TransferRequest;
import com.banking.transactions.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    //private TransactionController service;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Deposito
   @PostMapping("/accounts/{accountNumber}/deposit")
    public Mono<TransactionResponse> deposit(@PathVariable String accountNumber, @RequestBody AmountRequest request) {
     return transactionService.deposit(accountNumber, request);
    }

    // Retirar
   @PostMapping("/accounts/{accountNumber}/withdrawal")
    public Mono<TransactionResponse> withdrawal(@PathVariable String accountNumber, @RequestBody AmountRequest request) {
        return transactionService.withdrawal(accountNumber, request);
    }

    // Transferencia
    @PostMapping("/accounts/transfer")
    public Mono<TransactionResponse> transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }

    @GetMapping
    public Flux<TransactionResponse> getAllTransactions(){
        return transactionService.getAllTransactions();
    }


    @GetMapping("/accounts/{accountNumber}/history")
    public Flux<TransactionResponse> history(@PathVariable String accountNumber) {
        return transactionService.history(accountNumber);
    }
   
}















