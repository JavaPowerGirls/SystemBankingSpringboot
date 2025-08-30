package com.customer_ms.service.impl;

import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import org.springframework.stereotype.Service;
import com.customer_ms.service.ClientService;

import java.util.List;
import java.util.NoSuchElementException;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {

        // verificar si ya existe
        if(clientRepository.existsByDocumentId(client.getDocumentId())){
            throw new IllegalArgumentException("Client already exists");
        }

        // crear una variable de tipo cliente y se contruye el objeto atributo por atributo
        Client clientOpt = Client.builder().firstName(client.getFirstName()).
                lastName(client.getLastName()).documentId(client.getDocumentId()).
                email(client.getEmail()).build();

      // se guarda en el repositorio el el objeto cliente
       return clientRepository.save(clientOpt);
    }

    @Override
    public List<Client> listClients() {
        return null; // por mientras
    }

    @Override
    public Client getClient(int id) {
        return null; // por mientras
       /* return data.stream()
                .filter( dato -> dato.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe")); */
    }

    @Override
    public boolean deleteClient(int id) {
        return false; // por mientras
        /*return data.removeIf(cust -> cust.getId()==id);*/
    }

    @Override
    public Client update(Client client) {
        return null;
    }
}
