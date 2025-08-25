package com.bankingsystem.application.services;

import com.bankingsystem.domain.model.Client;
import com.bankingsystem.domain.services.ClientService;
import com.bankingsystem.infrastructure.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    //	El DNI debe ser único para cada cliente.
    //	El email debe tener un formato válido.
    //	No se permite crear un cliente sin todos los campos obligatorios (nombre, apellido, dni, email).


    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Long registerClient(Client client) {

        if(clientRepository.existsByDocumentId(client.getDocumentId())){
            throw new IllegalArgumentException("Client already exists");
        }

        try{
            clientRepository.save(client);
            return  client.getClientId();
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }



}
