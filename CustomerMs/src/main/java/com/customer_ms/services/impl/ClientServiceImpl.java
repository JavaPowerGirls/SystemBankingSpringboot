package com.customer_ms.services.impl;

import org.springframework.stereotype.Service;

import com.customer_ms.repository.ClientRepository;
import com.customer_ms.services.ClientService;


// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository; 

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository; 
    }
   
}
