package com.banking.transactions.service;

import com.banking.transactions.dto.AmountRequest;
import com.banking.transactions.dto.TransactionResponse;
import com.banking.transactions.dto.TransferRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

     Mono<TransactionResponse> deposit(String accountId, AmountRequest request);
     Mono<TransactionResponse> withdrawal(String accountId, AmountRequest request);
     Mono<TransactionResponse> transfer(TransferRequest request);
     Flux<TransactionResponse> history(String accountId);
     Flux<TransactionResponse> getAllTransactions();
}
