package com.bankingsystem.interfaces.rest.mappers;

import org.springframework.stereotype.Component;

import com.bankingsystem.domain.model.BankAccount;
import com.bankingsystem.interfaces.rest.dto.BankAccountResponseDTO;

// convierte los datos de una cuenta bancaria en un objeto dto
// que se puede enviar como respuesta JSON
@Component
public class BankAccountMapper {

    public BankAccountResponseDTO toDto(BankAccount bankAccount) {
        return new BankAccountResponseDTO(
            bankAccount.getAccountId(),
            bankAccount.getAccountNumber(),
            bankAccount.getBalance(),
            bankAccount.getAccountType().toString(),
            bankAccount.getClientId()
        );
    }
}