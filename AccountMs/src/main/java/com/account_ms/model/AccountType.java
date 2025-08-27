package com.account_ms.model;

public enum AccountType {
    SAVINGS(0),       // no permite saldo negativo
    CHECKING(-500);   // permite hasta -500 de sobregiro

    private final double overdraftLimit;

    AccountType(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}