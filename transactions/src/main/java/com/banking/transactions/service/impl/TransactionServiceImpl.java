package com.banking.transactions.service.impl;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.banking.transactions.dto.AmountRequest;
import com.banking.transactions.dto.TransactionResponse;
import com.banking.transactions.dto.TransferRequest;
import com.banking.transactions.mapper.TransactionMapper;
import com.banking.transactions.model.Transaction;
import com.banking.transactions.model.TransactionType;
import com.banking.transactions.repository.TransactionRepository;
import com.banking.transactions.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WebClient webClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WebClient webClient){
        this.transactionRepository = transactionRepository;
        this.webClient = webClient;
    }

    @Override
    public Mono<TransactionResponse> deposit(String accountNumber, AmountRequest request) {
        return callAccount("/api/v1/accounts/{accountNumber}/deposit", accountNumber, request)
                .flatMap(result -> {
                    Transaction transaction = Transaction.builder()
                            .type(TransactionType.DEPOSIT)
                            .sourceAccountNumber(accountNumber)
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                    return transactionRepository.save(transaction)
                            .map(TransactionMapper::toTransactionResponse);
                });
    }

    @Override
    public Mono<TransactionResponse> withdrawal(String accountNumber, AmountRequest request) {
        return callAccount("/api/v1/accounts/{accountNumber}/withdrawal", accountNumber, request)
                .flatMap(result -> {
                    Transaction transaction = Transaction.builder()
                            .type(TransactionType.WITHDRAWAL)
                            .sourceAccountNumber(accountNumber)
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                    return transactionRepository.save(transaction)
                            .map(TransactionMapper::toTransactionResponse);
                });
    }

    @Override
    public Mono<TransactionResponse> transfer(TransferRequest request) {
        AmountRequest amountRequest = new AmountRequest();
        amountRequest.setAmount(request.getAmount());

        // Realizar transferencia: retiro de origen y depósito en destino
        return callAccount("/api/v1/accounts/{accountNumber}/withdrawal", request.getSourceAccountNumber(), amountRequest)
                .flatMap(withdrawalResult -> 
                    callAccount("/api/v1/accounts/{accountNumber}/deposit", request.getDestinationAccountNumber(), amountRequest))
                .flatMap(depositResult -> {
                    Transaction transaction = Transaction.builder()
                            .type(TransactionType.TRANSFER)
                            .sourceAccountNumber(request.getSourceAccountNumber())
                            .destinationAccountNumber(request.getDestinationAccountNumber())
                            .amount(request.getAmount())
                            .date(LocalDate.now())
                            .build();
                    return transactionRepository.save(transaction)
                            .map(TransactionMapper::toTransactionResponse);
                });
    }

    @Override
    public Flux<TransactionResponse> history(String accountNumber) {
        return validateAccountExists(accountNumber)
                .thenMany(transactionRepository.findBySourceAccountNumberOrDestinationAccountNumberOrderByDateDesc(accountNumber, accountNumber)
                        .map(TransactionMapper::toTransactionResponse));
    }

    @Override
    public Flux<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .map(TransactionMapper::toTransactionResponse);
    }

    // Métodos helper privados para reducir duplicación

    private Mono<Object> callAccount(String uriTemplate, String accountNumber, AmountRequest request) {
        return webClient.put()
                .uri(uriTemplate, accountNumber)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(error -> 
                    Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation failed: " + error.getMessage())));
    }

    private Mono<Void> validateAccountExists(String accountNumber) {
        return webClient.get()
                .uri("/api/v1/accounts/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToMono(Object.class)
                .then()
                .onErrorResume(error -> 
                    Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with number " + accountNumber + " not found")));
    }
}
