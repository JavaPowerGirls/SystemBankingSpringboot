package com.banking.transaction_ms.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    // probar builder pattern completo
    @Test
    void builder_CreatesTransactionWithAllFields() {
        LocalDate date = LocalDate.now();

        Transaction transaction = Transaction.builder()
                .id("1")
                .sourceAccountNumber("ACC123")
                .destinationAccountNumber("ACC456")
                .amount(100.0)
                .date(date)
                .type(TransactionType.TRANSFER)
                .build();

        assertEquals("1", transaction.getId());
        assertEquals("ACC123", transaction.getSourceAccountNumber());
        assertEquals("ACC456", transaction.getDestinationAccountNumber());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(date, transaction.getDate());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
    }

    // crear transaccion deposito con builder
    @Test
    void builder_CreatesDepositTransaction() {
        Transaction transaction = Transaction.builder()
                .id("2")
                .sourceAccountNumber("ACC123")
                .amount(50.0)
                .date(LocalDate.now())
                .type(TransactionType.DEPOSIT)
                .build();

        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals("ACC123", transaction.getSourceAccountNumber());
        assertNull(transaction.getDestinationAccountNumber());
    }

    // verificar setters y getters
    @Test
    void settersAndGetters_WorkCorrectly() {
        Transaction transaction = Transaction.builder().build();

        transaction.setId("3");
        transaction.setSourceAccountNumber("ACC789");
        transaction.setAmount(200.0);
        transaction.setType(TransactionType.WITHDRAWAL);

        assertEquals("3", transaction.getId());
        assertEquals("ACC789", transaction.getSourceAccountNumber());
        assertEquals(200.0, transaction.getAmount());
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
    }
}