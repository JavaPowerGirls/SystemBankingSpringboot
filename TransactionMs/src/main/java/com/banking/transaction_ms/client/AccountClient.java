package com.banking.transaction_ms.client;

import com.banking.transaction_ms.dto.AmountRequest;
import reactor.core.publisher.Mono;

public interface AccountClient {
    Mono<Void> deposit(String accountNumber, AmountRequest request);
    Mono<Void> withdrawal(String accountNumber, AmountRequest request);
    Mono<Void> validateAccountExists(String accountNumber);
}