package com.bankingsystem.interfaces.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.domain.model.Client;
import com.bankingsystem.domain.services.ClientService;
import com.bankingsystem.interfaces.rest.dto.ClientRequestDTO;
import com.bankingsystem.interfaces.rest.dto.ClientResponseDTO;
import com.bankingsystem.interfaces.rest.mappers.ClientMapper;

// API REST para gestionar los clientes del banco
@RestController
@RequestMapping(value= "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE )
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    public ClientController(ClientService clientService, ClientMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }

    // Permite registrar un nuevo cliente en el banco
    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientRequestDTO requestDTO) {
        try {
            Client savedClient = clientService.registerClient(
                requestDTO.firstName, requestDTO.lastName, requestDTO.documentId, requestDTO.email
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(savedClient));
        } catch (IllegalArgumentException e) { // errores de negocio
            return ResponseEntity.badRequest().body(e.getMessage()); 
        } catch (Exception e) { // errores de sistema
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    // Obtiene la lista de todos los clientes registreados
    @GetMapping
    public ResponseEntity<?> getAllClients() {
        try {
            List<ClientResponseDTO> clients = clientService.getAllClients()
                .stream() // Convierte en stream
                .map(client -> mapper.toDto(client)) // Convierte en dto
                .collect(Collectors.toList()); // Convierte en lista
            
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}