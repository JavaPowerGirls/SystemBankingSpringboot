package com.demo.bank.transactionMs.service.impl;

import com.demo.bank.transactionMs.model.Transaction;
import com.demo.bank.transactionMs.model.TransactionType;
import com.demo.bank.transactionMs.repository.TransactionRepository;
import com.demo.bank.transactionMs.service.TransactionService;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Mono<Transaction> registerDeposit(String accountId, Double amount) {
        Transaction transaction = Transaction.builder().amount(amount).type(TransactionType.DEPOSIT).date(LocalDate.now()).accountOrigin(accountId).build();
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> registerWithdrawal(Double amount, String accountId) {
        Transaction transaction = Transaction.builder().amount(amount).type(TransactionType.WITHDRAWAL).date(LocalDate.now()).accountOrigin(accountId).build();
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> registerTransfer(String accountId, String accountDestination, Double amount) {
        Transaction transaction = Transaction.builder().amount(amount).type(TransactionType.TRANSFER).date(LocalDate.now()).accountOrigin(accountId).accountDestination(accountDestination).build();
        return transactionRepository.save(transaction);

    }

    @Override
    public Flux<Transaction> getTransactionHistory(String accountId) {
        return transactionRepository.findByAccountOrigin(accountId);
    }
}


