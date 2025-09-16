package com.banking.transaction_ms.client.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.banking.transaction_ms.client.AccountClient;
import com.banking.transaction_ms.dto.AmountRequest;

import reactor.core.publisher.Mono;

@Component
public class AccountClientImpl implements AccountClient {

    private final WebClient webClient;

    public AccountClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> deposit(String accountNumber, AmountRequest request) {
        return webClient.put()
                .uri("/api/v1/accounts/{accountNumber}/deposit", accountNumber)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Object.class)
                .then()
                .onErrorResume(error ->
                    Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Deposit operation failed: " + error.getMessage())));
    }

    @Override
    public Mono<Void> withdrawal(String accountNumber, AmountRequest request) {
        return webClient.put()
                .uri("/api/v1/accounts/{accountNumber}/withdrawal", accountNumber)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Object.class)
                .then()
                .onErrorResume(error ->
                    Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Withdrawal operation failed: " + error.getMessage())));
    }

    @Override
    public Mono<Void> validateAccountExists(String accountNumber) {
        return webClient.get()
                .uri("/api/v1/accounts/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToMono(Object.class)
                .then()
                .onErrorResume(error ->
                    Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with number " + accountNumber + " not found")));
    }
}