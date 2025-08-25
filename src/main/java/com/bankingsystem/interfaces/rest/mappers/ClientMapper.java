package com.bankingsystem.interfaces.rest.mappers;

import org.springframework.stereotype.Component;

import com.bankingsystem.domain.model.Client;
import com.bankingsystem.interfaces.rest.dto.ClientResponseDTO;

// convierte los datos de un cliente en un objeto dto
@Component
public class ClientMapper {

    public ClientResponseDTO toDto(Client client) {
        return new ClientResponseDTO(
            client.getClientId(),
            client.getFirstName(),
            client.getLastName(),
            client.getDocumentId(),
            client.getEmail()
        );
    }
}