package com.demo.bank.transactionMs.repository;

import com.demo.bank.transactionMs.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findByAccountOrigin(String accountOrigin);

}

