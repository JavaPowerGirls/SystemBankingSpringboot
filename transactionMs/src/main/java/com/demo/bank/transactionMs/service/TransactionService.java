package com.demo.bank.transactionMs.service;

import com.demo.bank.transactionMs.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Transaction> registerDeposit(String accountId, Double amount);
    Mono<Transaction> registerWithdrawal(Double amount , String accountId);
    Mono<Transaction> registerTransfer(String accountId , String accountDestination , Double amount );
    Flux<Transaction> getTransactionHistory(String accountId);
}
