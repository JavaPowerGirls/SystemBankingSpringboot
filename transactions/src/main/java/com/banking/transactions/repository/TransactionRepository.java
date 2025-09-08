package com.banking.transactions.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.banking.transactions.model.Transaction;

import reactor.core.publisher.Flux;


@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findBySourceAccountIdOrderByDate(String sourceAccountId);
    Flux<Transaction> findByDestinationAccountIdOrderByDate(String destinationAccountId);
    Flux<Transaction> findBySourceAccountIdOrDestinationAccountIdOrderByDateDesc(String sourceAccountId, String destinationAccountId);
}
