package com.customer_ms.controller;

import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/Clients")
@RestController
public class ClientController {

    // Variable y estanciando un servicio
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/hola")
    public String holamundo() {
        return "hola";
    }

    @PostMapping
    public Client createClient(@RequestHeader Map<String, String> headers, @RequestBody Client client) {
        return this.clientService.create(client);
    }


    // falta manejo de excepciones, validar datos de entrada, implementar datos


    //Endpoint para listar clientes
    @GetMapping
    public List<Client> getAllClients() {
        return this.clientService.listClients();
    }

    // Actualización de un registro de cliente
    @PutMapping("/{id}")
    public Client updateClient(@RequestHeader Map<String, String> headers, @PathVariable int id, @RequestBody Client client) {
        client.setClientId((long) id);
        return this.clientService.update(client);
    }
    // }

    //obtener un  registro de un Cliente by id
    @GetMapping("/{id}")
    public Client viewClient(@RequestHeader Map<String, String> headers, @PathVariable int id) {
        return this.clientService.getClient(id);
    }
// Eliminar un registro
    @DeleteMapping("/{id}")
    public boolean deleteClient(@RequestHeader Map<String, String> headers, @PathVariable int id) {
        return clientService.deleteClient(id);
    }
}