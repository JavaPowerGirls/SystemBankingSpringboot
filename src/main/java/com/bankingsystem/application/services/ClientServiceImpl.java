package com.bankingsystem.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bankingsystem.domain.model.Client;
import com.bankingsystem.domain.services.ClientService;
import com.bankingsystem.infrastructure.ClientRepository;


// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
@Transactional 
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository; 

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository; 
    }
 
    // Registra un nuevo cliente verificando que no exista otro con el mismo documento
    @Override
    public Client registerClient(String firstName, String lastName, String documentId, String email) {
        
        // Verifica si el cliente ya existe
        if (clientRepository.existsByDocumentId(documentId)) {
            throw new IllegalArgumentException("Client with document ID " + documentId + " already exists");
        }

        // Crea un nuevo cliente
        Client client = new Client(firstName, lastName, documentId, email);

        try {
            Client savedClient = clientRepository.save(client); // Guarda el cliente
            return savedClient;
        } catch (Exception e) {
            throw new RuntimeException("Error saving client: " + e.getMessage(), e);
        }
    }
    
    // Obtiene todos los clientes registrados
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll(); // Obtiene todos los clientes
    }
   
}
