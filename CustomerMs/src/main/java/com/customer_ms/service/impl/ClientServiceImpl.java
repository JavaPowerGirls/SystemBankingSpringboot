package com.customer_ms.service.impl;

import com.customer_ms.dto.ClientMapper;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.customer_ms.service.ClientService;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final RestTemplate restTemplate;
    
    @Value("${account.service.url:http://localhost:8082}")
    private String accountServiceUrl;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.restTemplate = restTemplate;
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
          return clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @Override
    public void deleteClient(Long id) {
        // Verificar que el cliente existe usando getClient
        getClient(id);
            
        // REGLA DE NEGOCIO: No se permite eliminar un cliente si tiene cuentas activas
        try {
            List<?> accounts = restTemplate.getForObject(accountServiceUrl + "/api/v1/accounts/clients/" + id, List.class);
            if (accounts != null && !accounts.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete client with active bank accounts");
            }
        } catch (RestClientException e) {
            // Si hay error de conexión, no permitir eliminar (fail-safe)
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Cannot verify accounts - deletion not allowed");
        }
        
        clientRepository.deleteById(id);
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

        clientMapper.updateEntityFromDto(request,client);
        return clientRepository.save(client);
    }

}
