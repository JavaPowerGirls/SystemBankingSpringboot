package com.customer_ms.service.impl;

import com.customer_ms.client.AccountServiceClient;
import com.customer_ms.dto.ClientMapper;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import org.springframework.stereotype.Service;
import com.customer_ms.service.ClientService;

import java.util.List;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AccountServiceClient accountServiceClient;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, AccountServiceClient accountServiceClient) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountServiceClient = accountServiceClient;
    }

    @Override
    public Client create(ClientRequest request) {
        if (clientRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("Dni already exists");
        }
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Client client = clientMapper.toEntity(request);
        return clientRepository.save(client);
    }

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @Override
    public void deleteClient(Long id) {
        // Verificar si el cliente existe
        if(!clientRepository.existsById(id))
            throw new IllegalArgumentException("Client with ID '" + id + "' does not exist.");
            
        // REGLA DE NEGOCIO: No se permite eliminar un cliente si tiene cuentas activas
        if (accountServiceClient.hasActiveBankAccounts(id)) {
            throw new IllegalArgumentException("Cannot delete client with active bank accounts");
        }
        
        clientRepository.deleteById(id);
    }

    @Override
    public Client update(Long id, ClientRequest request) {

        var client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));

        if (clientRepository.existsByDniAndIdNot(request.getDni(), id)) {
            throw new IllegalArgumentException("DNI already used by another customer");
        }
        if (clientRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email already used by another customer");
        }

       clientMapper.updateEntityFromDto(request,client);
        return clientRepository.save(client);
    }

}
