package com.customer_ms.services.impl;

import org.springframework.stereotype.Service;
import com.customer_ms.repository.ClientRepository;
import com.customer_ms.services.ClientService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

// Implementa la lógica de negocio para gestionar clientes del banco
@Service 
public class ClientServiceImpl implements ClientService {
    public static List<Client> data =new ArrayList<>(List.of(
            Client.builder(). id(1).name("Jessica").lastname("Chanco").dni("40602678").email("jessicachanco@gmail.com").build(),
            Client.builder(). id(2).name("Karen").lastname("Duran Villa").dni("74090214").email("karenmelisavilla01@gmail.com").build(),
            Client.builder(). id(3).name("Tatiana").lastname("Paucar").dni("76089765").email("tatianapaucar@gmail.com").build()));
    @Override
    public List<Client> ListClient() {
        return data;
    }

    @Override
    public Client getClient(int id) {
        return data.stream()
                .filter( dato -> dato.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe"));
    }
    @Override
    public boolean deleteClient(int id) {
        return data.removeIf(cust -> cust.getId()==id);
    }
}
