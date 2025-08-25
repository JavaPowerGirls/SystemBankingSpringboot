package com.bankingsystem.domain.model;

public class Client {
    // Atributes
    private int clientId;
    private String firstName;
    private String lastName;
    private String documentId;   // e.g. ID card, passport, etc.
    private String email;

// Constructor with parameters

    public Client(int clientId, String firstName, String lastName, String documentId, String email) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.email = email;
    }

    // Getters and Setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    // Method to display information (Método para mostrar la información)
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
