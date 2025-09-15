package com.banking.transaction_ms.service.impl;

import com.banking.transaction_ms.client.AccountClient;
import com.banking.transaction_ms.dto.AmountRequest;
import com.banking.transaction_ms.dto.TransactionResponse;
import com.banking.transaction_ms.dto.TransferRequest;
import com.banking.transaction_ms.model.Transaction;
import com.banking.transaction_ms.model.TransactionType;
import com.banking.transaction_ms.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private AmountRequest amountRequest;
    private TransferRequest transferRequest;

    // configurar objetos para tests reactivos
    @BeforeEach
    void setUp() {
        transaction = Transaction.builder()
                .id("1")
                .type(TransactionType.DEPOSIT)
                .sourceAccountNumber("ACC123")
                .amount(100.0)
                .date(LocalDate.now())
                .build();

        amountRequest = new AmountRequest();
        amountRequest.setAmount(100.0);

        transferRequest = new TransferRequest();
        transferRequest.setSourceAccountNumber("ACC123");
        transferRequest.setDestinationAccountNumber("ACC456");
        transferRequest.setAmount(200.0);
    }

    // servicio deposito reactivo
    @Test
    void deposit() {
        // mock del cliente de cuentas
        when(accountClient.deposit(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.empty());
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(Mono.just(transaction));

        Mono<TransactionResponse> result = transactionService.deposit("ACC123", amountRequest);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getType() == TransactionType.DEPOSIT &&
                        response.getAmount().equals(100.0) &&
                        response.getSourceAccountNumber().equals("ACC123"))
                .verifyComplete();
    }

    // servicio retiro con mono
    @Test
    void withdrawal() {
        Transaction withdrawalTransaction = Transaction.builder()
                .id("2")
                .type(TransactionType.WITHDRAWAL)
                .sourceAccountNumber("ACC123")
                .amount(50.0)
                .date(LocalDate.now())
                .build();

        when(accountClient.withdrawal(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.empty());
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(Mono.just(withdrawalTransaction));

        AmountRequest withdrawalRequest = new AmountRequest();
        withdrawalRequest.setAmount(50.0);

        Mono<TransactionResponse> result = transactionService.withdrawal("ACC123", withdrawalRequest);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getType() == TransactionType.WITHDRAWAL &&
                        response.getAmount().equals(50.0) &&
                        response.getSourceAccountNumber().equals("ACC123"))
                .verifyComplete();
    }

    // servicio transferencia completa
    @Test
    void transfer() {
        Transaction transferTransaction = Transaction.builder()
                .id("3")
                .type(TransactionType.TRANSFER)
                .sourceAccountNumber("ACC123")
                .destinationAccountNumber("ACC456")
                .amount(200.0)
                .date(LocalDate.now())
                .build();

        when(accountClient.validateAccountExists(anyString()))
                .thenReturn(Mono.empty());
        when(accountClient.withdrawal(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.empty());
        when(accountClient.deposit(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.empty());
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(Mono.just(transferTransaction));

        Mono<TransactionResponse> result = transactionService.transfer(transferRequest);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getType() == TransactionType.TRANSFER &&
                        response.getAmount().equals(200.0) &&
                        response.getSourceAccountNumber().equals("ACC123") &&
                        response.getDestinationAccountNumber().equals("ACC456"))
                .verifyComplete();
    }

    // historial con flux
    @Test
    void history() {
        when(accountClient.validateAccountExists(anyString()))
                .thenReturn(Mono.empty());
        when(transactionRepository.findBySourceAccountNumberOrDestinationAccountNumberOrderByDateDesc(
                anyString(), anyString()))
                .thenReturn(Flux.just(transaction));

        Flux<TransactionResponse> result = transactionService.history("ACC123");

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getSourceAccountNumber().equals("ACC123"))
                .verifyComplete();
    }

    // listar todas con flux
    @Test
    void getAllTransactions() {
        when(transactionRepository.findAll())
                .thenReturn(Flux.just(transaction));

        Flux<TransactionResponse> result = transactionService.getAllTransactions();

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getId().equals("1") &&
                        response.getType() == TransactionType.DEPOSIT)
                .verifyComplete();
    }
}