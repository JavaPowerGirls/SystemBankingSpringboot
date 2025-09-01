package com.customer_ms.service;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import java.util.List;
import java.util.Optional;


// Define las operaciones para gestionar los clientes del banco
public interface ClientService {
    Client create(ClientRequest client);
    Client update(Long id, ClientRequest client);
    Client getClient(Long id);
    void deleteClient(Long id);
    List<Client> listClients();
}
