package com.example.transaction.services;

import com.example.transaction.dto.TransactionDto;
import com.example.transaction.dto.TransferRequest;
import com.example.transaction.dto.WithdrawRequest;
import com.example.transaction.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TransactionService {

        Mono<Transaction> deposit(TransactionDto request);

        Mono<Transaction> withdraw(WithdrawRequest request);

        Mono<Transaction> transfer(TransferRequest request);

        Flux<Transaction> getAll();

        Mono<Transaction> getById(String id);

        Mono<Void> delete(String id);

        Flux<Transaction> getAllTransactions();

    Flux<Transaction> history(TransferRequest request);
}