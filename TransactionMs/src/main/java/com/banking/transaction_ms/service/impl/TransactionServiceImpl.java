package com.banking.transaction_ms.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.banking.transaction_ms.client.AccountClient;
import com.banking.transaction_ms.dto.AmountRequest;
import com.banking.transaction_ms.dto.TransactionResponse;
import com.banking.transaction_ms.dto.TransferRequest;
import com.banking.transaction_ms.mapper.TransactionMapper;
import com.banking.transaction_ms.model.Transaction;
import com.banking.transaction_ms.model.TransactionType;
import com.banking.transaction_ms.repository.TransactionRepository;
import com.banking.transaction_ms.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                AccountClient accountClient) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
    }

    @Override
    public Mono<TransactionResponse> deposit(String accountNumber, AmountRequest request) {
        return accountClient.deposit(accountNumber, request)
                .then(Mono.fromCallable(() -> {
                    return Transaction.builder()
                            .type(TransactionType.DEPOSIT)
                            .sourceAccountNumber(accountNumber)
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                }))
                .flatMap(transaction -> transactionRepository.save(transaction)
                        .map(TransactionMapper::toTransactionResponse));
    }

    @Override
    public Mono<TransactionResponse> withdrawal(String accountNumber, AmountRequest request) {
        return accountClient.withdrawal(accountNumber, request)
                .then(Mono.fromCallable(() -> {
                    return Transaction.builder()
                            .type(TransactionType.WITHDRAWAL)
                            .sourceAccountNumber(accountNumber)
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                }))
                .flatMap(transaction -> transactionRepository.save(transaction)
                        .map(TransactionMapper::toTransactionResponse));
    }

    @Override
    public Mono<TransactionResponse> transfer(TransferRequest request) {
        AmountRequest amountRequest = new AmountRequest();
        amountRequest.setAmount(request.getAmount());

        return accountClient.validateAccountExists(request.getSourceAccountNumber())
                .then(Mono.defer(() -> accountClient.validateAccountExists(request.getDestinationAccountNumber())))
                .then(Mono.defer(() -> accountClient.withdrawal(request.getSourceAccountNumber(), amountRequest)))
                .then(Mono.defer(() -> accountClient.deposit(request.getDestinationAccountNumber(), amountRequest)))
                .then(Mono.fromCallable(() -> {
                    return Transaction.builder()
                            .type(TransactionType.TRANSFER)
                            .sourceAccountNumber(request.getSourceAccountNumber())
                            .destinationAccountNumber(request.getDestinationAccountNumber())
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                }))
                .flatMap(transaction -> transactionRepository.save(transaction)
                        .map(TransactionMapper::toTransactionResponse));
    }

    @Override
    public Flux<TransactionResponse> history(String accountNumber) {
        return accountClient.validateAccountExists(accountNumber)
                .thenMany(Flux.defer(() -> transactionRepository
                        .findBySourceAccountNumberOrDestinationAccountNumberOrderByDateDesc(
                                accountNumber, accountNumber)
                        .map(TransactionMapper::toTransactionResponse)));
    }

    @Override
    public Flux<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .map(TransactionMapper::toTransactionResponse);
    }

}