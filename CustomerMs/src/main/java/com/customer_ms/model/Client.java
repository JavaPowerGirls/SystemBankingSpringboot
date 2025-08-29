package com.customer_ms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

// Representa un cliente del banco que puede abrir y manejar cuentas bancarias
@Entity
@Table(name = "clients")
public class Client {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String documentId; // e.g. ID card, passport, etc.

    @Column(nullable = false)
    private String email;

    public Client() {}

    // Constructor con parametros
    public Client(String firstName, String lastName, String documentId, String email) {
        validate(firstName, lastName, documentId, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.email = email;
    }

    private void validate(String firstName, String lastName, String documentId, String email) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name is required");
        if (documentId == null || documentId.isBlank())
            throw new IllegalArgumentException("Document ID is required");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is required");
        if (!email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("Invalid email format");
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

    //// Metodo para mostrar la informacion del cliente
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
