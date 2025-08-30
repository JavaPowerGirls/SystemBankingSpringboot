package com.customer_ms.controller;

import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/clients")
@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // falta manejo de excepciones, validar datos de entrada, implmentar dtos

    @PostMapping
    public Client createClient(@RequestHeader Map<String, String> headers, @RequestBody Client client) {

       return this.clientService.create(client);
    }

    //listar a todos los clientes
    @GetMapping
    public List<Client> viewClients() {
        return clientService.listClients();
    }

    @PutMapping
    public Client updateClient(@RequestHeader Map<String, String> headers, @RequestBody Client client) {
        return clientService.update(client);
    }

    //obtener un cliente by id
    @GetMapping("/{id}")
    public Client viewClient (@RequestHeader Map<String, String> headers, @PathVariable int id) {
        return clientService.getClient(id);
    }
    @DeleteMapping("/{id}")
    public boolean deleteClient(@RequestHeader Map<String, String> headers, @PathVariable int id) {
        return clientService.deleteClient(id);
    }

}