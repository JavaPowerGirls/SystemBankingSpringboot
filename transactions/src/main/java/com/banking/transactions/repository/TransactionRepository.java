package com.banking.transactions.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.transactions.model.Transaction;

import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findByAccountOrigin(String accountOrigin);
    Flux<Transaction> findBySourceAccountNumberOrderByDate(String sourceAccountNumber);
    Flux<Transaction> findByDestinationAccountNumberOrderByDate(String destinationAccountNumber);
    Flux<Transaction> findBySourceAccountNumberOrDestinationAccountNumberOrderByDateDesc(String sourceAccountNumber, String destinationAccountNumber);

}

