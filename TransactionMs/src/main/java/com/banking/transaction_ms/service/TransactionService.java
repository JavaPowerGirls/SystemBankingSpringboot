package com.banking.transaction_ms.service;

import com.banking.transaction_ms.dto.AmountRequest;
import com.banking.transaction_ms.dto.TransactionResponse;
import com.banking.transaction_ms.dto.TransferRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

     Mono<TransactionResponse> deposit(String accountNumber, AmountRequest request);
     Mono<TransactionResponse> withdrawal(String accountNumber, AmountRequest request);
     Mono<TransactionResponse> transfer(TransferRequest request);
     Flux<TransactionResponse> history(String accountNumber);
     Flux<TransactionResponse> getAllTransactions();
}