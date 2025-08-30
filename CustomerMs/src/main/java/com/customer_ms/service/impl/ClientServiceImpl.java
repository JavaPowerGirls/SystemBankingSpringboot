package com.customer_ms.service.impl;

import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import org.springframework.stereotype.Service;
import com.customer_ms.service.ClientService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {
        if (clientRepository.existsByDocumentId(client.getDocumentId())) {
            throw new IllegalArgumentException("Client already exists");
        }

        Client clientOpt = Client.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .documentId(client.getDocumentId())
                .email(client.getEmail())
                .build();

        return clientRepository.save(clientOpt);
    }

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(int id) {
        //return null;
        return clientRepository.findById((long) id).orElse(null); // Se utiliza Optional.orElse(null) para devolver el cliente
    }

    @Override
    public boolean deleteClient(int id) {
        if (clientRepository.existsById((long) id)) {
            clientRepository.deleteById((long) id);
            return true;
        }
        return false;
    }

    @Override
    public Client update(Client client) {
        Optional<Client> optional = clientRepository.findById(client.getClientId());
        if (optional.isPresent()) {
            Client existing = optional.get();
            existing.setClientId(client.getClientId());
            existing.setFirstName(client.getFirstName());
            existing.setLastName(client.getLastName());
            existing.setDocumentId(client.getDocumentId());
            existing.setEmail(client.getEmail());
            return clientRepository.save(existing);
        }
        return null;
    }

}
