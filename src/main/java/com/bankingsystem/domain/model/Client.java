package com.bankingsystem.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Representa un cliente del banco que puede abrir y manejar cuentas bancarias
// Decidí usar JPA para persistir los datos porque facilita el manejo de la base de datos
@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String documentId;   // DNI - debe ser único
    
    @Column(nullable = false)
    private String email;


    // Constructor por defecto para JPA
    public Client() {}


    // Constructor para crear un nuevo cliente con validaciones de negocio
    // Valido todos los campos porque son obligatorios para abrir cuentas bancarias
    public Client(String firstName, String lastName, String documentId, String email) {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (documentId == null || documentId.isBlank()) throw new IllegalArgumentException("Document ID is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (!email.contains("@") || !email.contains(".")) throw new IllegalArgumentException("Invalid email format");
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.email = email;
    }



    // Getters and Setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getEmail() {
        return email;
    }

    
    // Muestra la información del cliente de forma legible para debugging
    // Es útil para logs y pruebas durante el desarrollo
    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
