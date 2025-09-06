package com.account_ms.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "bank_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    public BankAccount(Long clientId, AccountType accountType) {
        this.clientId = clientId;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.accountType = accountType;
    }

    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        validateWithdraw(amount);
        this.balance -= amount;
    }

    private void validateWithdraw(double amount) {
        double newBalance = this.balance - amount;
        if (accountType == AccountType.SAVINGS && newBalance < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Savings accounts cannot have negative balance");
        }

        if (accountType == AccountType.CHECKING && newBalance < -500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking accounts cannot exceed overdraft limit of -500");
        }

    }
}
