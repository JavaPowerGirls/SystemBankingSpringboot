package com.banking.transaction_ms.controller;

import com.banking.transaction_ms.dto.AmountRequest;
import com.banking.transaction_ms.dto.TransactionResponse;
import com.banking.transaction_ms.dto.TransferRequest;
import com.banking.transaction_ms.model.TransactionType;
import com.banking.transaction_ms.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private TransactionService transactionService;


    // test deposito con webflux
    @Test
    void deposit() {
        AmountRequest request = new AmountRequest();
        request.setAmount(100.0);

        TransactionResponse response = TransactionResponse.builder()
                .id("1")
                .type(TransactionType.DEPOSIT)
                .sourceAccountNumber("ACC123")
                .amount(100.0)
                .date(LocalDate.now())
                .build();

        when(transactionService.deposit(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/v1/transactions/accounts/ACC123/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("DEPOSIT")
                .jsonPath("$.amount").isEqualTo(100);
    }

    // test retiro reactivo
    @Test
    void withdrawal() {
        AmountRequest request = new AmountRequest();
        request.setAmount(50.0);

        TransactionResponse response = TransactionResponse.builder()
                .id("2")
                .type(TransactionType.WITHDRAWAL)
                .sourceAccountNumber("ACC123")
                .amount(50.0)
                .date(LocalDate.now())
                .build();

        when(transactionService.withdrawal(anyString(), any(AmountRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/v1/transactions/accounts/ACC123/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("WITHDRAWAL")
                .jsonPath("$.amount").isEqualTo(50);
    }

    // test transferencia entre cuentas
    @Test
    void transfer() {
        TransferRequest request = new TransferRequest();
        request.setSourceAccountNumber("ACC123");
        request.setDestinationAccountNumber("ACC456");
        request.setAmount(200.0);

        TransactionResponse response = TransactionResponse.builder()
                .id("3")
                .type(TransactionType.TRANSFER)
                .sourceAccountNumber("ACC123")
                .destinationAccountNumber("ACC456")
                .amount(200.0)
                .date(LocalDate.now())
                .build();

        when(transactionService.transfer(any(TransferRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/v1/transactions/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("TRANSFER")
                .jsonPath("$.amount").isEqualTo(200);
    }

    // obtener todas las transacciones
    @Test
    void getAllTransactions() {
        TransactionResponse response = TransactionResponse.builder()
                .id("1")
                .type(TransactionType.DEPOSIT)
                .sourceAccountNumber("ACC123")
                .amount(100.0)
                .date(LocalDate.now())
                .build();

        when(transactionService.getAllTransactions())
                .thenReturn(Flux.just(response));

        webTestClient.get()
                .uri("/api/v1/transactions")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionResponse.class)
                .hasSize(1);
    }

    // historial de transacciones por cuenta
    @Test
    void history() {
        TransactionResponse response = TransactionResponse.builder()
                .id("1")
                .type(TransactionType.DEPOSIT)
                .sourceAccountNumber("ACC123")
                .amount(100.0)
                .date(LocalDate.now())
                .build();

        when(transactionService.history(anyString()))
                .thenReturn(Flux.just(response));

        webTestClient.get()
                .uri("/api/v1/transactions/accounts/ACC123/history")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionResponse.class)
                .hasSize(1);
    }
}