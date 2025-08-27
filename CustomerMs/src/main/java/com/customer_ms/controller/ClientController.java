package com.customer_ms.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer_ms.services.ClientService; 

// API REST para gestionar los clientes del banco
@RestController
@RequestMapping(value= "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE )
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    } 
}