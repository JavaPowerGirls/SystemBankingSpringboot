package com.banking.transactions.service;

import com.banking.transactions.dto.AmountRequest;
import com.banking.transactions.dto.TransactionResponse;
import com.banking.transactions.dto.TransferRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

     Mono<TransactionResponse> deposit(String accountNumber, AmountRequest request);
     Mono<TransactionResponse> withdrawal(String accountNumber, AmountRequest request);
     Mono<TransactionResponse> transfer(TransferRequest request);
     Flux<TransactionResponse> history(String accountNumber);
     Flux<TransactionResponse> getAllTransactions();
}
