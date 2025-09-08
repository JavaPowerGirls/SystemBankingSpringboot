package com.example.transaction.services.impl;

import com.example.transaction.dto.TransactionDto;
import org.springframework.stereotype.Service;
import com.example.transaction.dto.TransferRequest;
import com.example.transaction.dto.WithdrawRequest;
import com.example.transaction.model.Transaction;
import com.example.transaction.model.TransactionType;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.services.TransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.repository = transactionRepository;
    }

    @Override
    public Mono<Transaction> deposit(TransactionDto request) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(request.getAmount()); // si usas Double en Transaction
        transaction.setAccountFrom(request.getAccountFrom());
        transaction.setAccountTo(request.getAccountTo());
        transaction.setDate(LocalDateTime.now());
        return repository.save(transaction);
    }

    @Override
    public Mono<Transaction> withdraw(WithdrawRequest request) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(request.getAmount());
        transaction.setAccountFrom(request.getAccountFrom());
        transaction.setDate(LocalDateTime.now());
        return repository.save(transaction);
    }

    @Override
    public Mono<Transaction> transfer(TransferRequest request) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(request.getAmount());
        transaction.setAccountFrom(request.getAccountFrom());
        transaction.setAccountTo(request.getAccountTo());
        transaction.setDate(LocalDateTime.now());
        return repository.save(transaction);
    }

    @Override
    public Flux<Transaction> getAll() {
        return null;
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public Flux<Transaction> history(TransferRequest request) {
        return null;
    }

    @Override
        public Mono<Transaction> getById(String id) {
            return repository.findById(id);
        }

        @Override
        public Mono<Void> delete(String id) {
            return repository.deleteById(id);
        }
    }
