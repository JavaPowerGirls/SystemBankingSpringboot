package com.customer_ms.dto;

import com.customer_ms.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequest request) {
        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dni(request.getDni())
                .build();

    }

    public void updateEntityFromDto(ClientRequest dto, Client client) {
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setDni(dto.getDni());
        client.setEmail(dto.getEmail());
    }
}
