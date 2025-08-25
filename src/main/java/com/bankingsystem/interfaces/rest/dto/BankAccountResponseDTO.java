package com.bankingsystem.interfaces.rest.dto;

// respuesta de la API 
// Transporta la información completa de una cuenta bancaria
public class BankAccountResponseDTO {
    public Long accountId;
    public String accountNumber;
    public Double balance;
    public String accountType;
    public Long clientId;


    public BankAccountResponseDTO(Long accountId, String accountNumber, Double balance, 
                                 String accountType, Long clientId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.clientId = clientId;
    }
}
