package com.customer_ms.service;
import com.customer_ms.model.Client;
import java.util.List;


// Define las operaciones para gestionar los clientes del banco
public interface ClientService {
    Client create(Client client);
    List<Client> listClients();
    Client getClient(int id);
    boolean deleteClient(int id);
    Client update(Client client);
}
