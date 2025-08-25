package com.bankingsystem.interfaces.rest.dto;

// respuesta de la API
// Transporta la información completa de un cliente
public class ClientResponseDTO {
    public Long clientId;
    public String firstName;
    public String lastName;
    public String documentId;
    public String email;


    public ClientResponseDTO(Long clientId, String firstName, String lastName, String documentId, String email) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.email = email;
    }
}
