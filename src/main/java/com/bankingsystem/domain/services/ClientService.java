package com.bankingsystem.domain.services;

import java.util.List;

import com.bankingsystem.domain.model.Client;

// Define las operaciones para gestionar los clientes del banco
public interface ClientService {
 
    // Registra un nuevo cliente
    Client registerClient(String firstName, String lastName, String documentId, String email);
    
    // Obtiene la lista de todos los clientes
    List<Client> getAllClients();
    
     
}
