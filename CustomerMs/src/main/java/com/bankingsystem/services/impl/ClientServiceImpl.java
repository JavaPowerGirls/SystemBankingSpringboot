package com.bankingsystem.services.impl;

import org.springframework.stereotype.Service;

import com.bankingsystem.repository.ClientRepository;
import com.bankingsystem.services.ClientService;


// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository; 

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository; 
    }
   
}
