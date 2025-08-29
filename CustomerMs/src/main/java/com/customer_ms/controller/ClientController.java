package com.customer_ms.controller;

import com.customer_ms.model.Client;
import com.customer_ms.services.ClientService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// API REST para gestionar los clientes del banco
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public List<Client> viewClients(@RequestHeader Map<String, String> headers) {
        return listClients();
    }

    @GetMapping("/clients"/{id}")" +
        "public Client viewClient (@RequestHeader Map<String, String> headers, @PathVariable int id) { " +
        "return clientService.getClient(id);
    }
    @DeleteMapping("/clients/{id}")
    public boolean deleteClient(@RequestHeader Map<String, String> headers, @PathVariable int id) {
    return ClientService.impl.ClientService.deleteClient(id);
    }
        @GetMapping
        public String saludar(){
            return "Hola Mundo";
        }
}