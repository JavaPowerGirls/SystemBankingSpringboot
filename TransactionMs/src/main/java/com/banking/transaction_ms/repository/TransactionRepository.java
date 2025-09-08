package com.banking.transaction_ms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.transaction_ms.model.Transaction;

import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findBySourceAccountNumberOrderByDate(String sourceAccountNumber);
    Flux<Transaction> findByDestinationAccountNumberOrderByDate(String destinationAccountNumber);
    Flux<Transaction> findBySourceAccountNumberOrDestinationAccountNumberOrderByDateDesc(String sourceAccountNumber, String destinationAccountNumber);

}