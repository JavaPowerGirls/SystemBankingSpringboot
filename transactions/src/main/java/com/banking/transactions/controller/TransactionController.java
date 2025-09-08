package com.banking.transactions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts/{accountId}")
    public Flux<TransactionResponse> history(@PathVariable String accountId) {
        return transactionService.history(accountId);
    }

    @GetMapping
    public Flux<TransactionResponse> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @PostMapping("/accounts/{accountId}/deposit")
    public Mono<TransactionResponse> deposit(@PathVariable String accountId, @RequestBody AmountRequest request){
        return transactionService.deposit(accountId, request);
    }

    @PostMapping("/accounts/{accountId}/withdrawal")
    public Mono<TransactionResponse> withdrawal(@PathVariable String accountId, @RequestBody AmountRequest request){
        return transactionService.withdrawal(accountId, request);
    }

    @PostMapping("/accounts/transfer")
    public Mono<TransactionResponse> transfer(@RequestBody TransferRequest request){
        return transactionService.transfer(request);
    }
}
