package com.customer_ms.service.impl;

import com.customer_ms.client.AccountClient;
import com.customer_ms.dto.ClientMapper;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import com.customer_ms.service.ClientService;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AccountClient accountClient;

    public ClientServiceImpl(ClientRepository clientRepository,
                            ClientMapper clientMapper,
                            AccountClient accountClient) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountClient = accountClient;
    }

    @Override
    public Client create(ClientRequest request) {
        if (clientRepository.existsByDni(request.getDni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI already exists");
        }
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        return clientRepository.save(clientMapper.toEntity(request));
    }

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Long id) {
          return clientRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @Override
    public void deleteClient(Long id) {
        // Verificar que el cliente existe
        Client client = getClient(id);

        // REGLA DE NEGOCIO: No se permite eliminar un cliente si tiene cuentas activas
        if (!accountClient.canDeleteClient(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Cannot delete client with active bank accounts");
        }

        clientRepository.delete(client);
    }

    @Override
    public Client update(Long id, ClientRequest request) {
        // Verificar que el cliente existe usando getClient
        Client client = getClient(id);

        if (clientRepository.existsByDniAndIdNot(request.getDni(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI already used by another customer");
        }
        if (clientRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already used by another customer");
        }

        clientMapper.updateEntityFromDto(request, client);
        return clientRepository.save(client);
    }

}
