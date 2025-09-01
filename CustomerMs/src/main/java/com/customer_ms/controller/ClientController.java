package com.customer_ms.controller;

import com.customer_ms.dto.ClientMapper;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/clients")
@RestController
public class ClientController {

    // Variable y estanciando un servicio
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientRequest request) {
        var savedClient = clientService.create(request);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
    // falta manejo de excepciones, validar datos de entrada, implementar datos


    //Endpoint para listar clientes
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        var clients = clientService.listClients();
        return ResponseEntity.ok(clients);
    }

    // Actualización de un registro de cliente
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.update(id, request));
    }

    //obtener un  registro de un Cliente by id
    @GetMapping("/{id}")
    public ResponseEntity<Client>  viewClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    // Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with given id successfully deleted");
    }
}