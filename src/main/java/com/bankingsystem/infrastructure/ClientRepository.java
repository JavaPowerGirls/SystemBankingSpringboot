package com.bankingsystem.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsystem.domain.model.Client;

// Maneja el acceso a datos de clientes
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Verifica si ya existe un cliente con el mismo dni
    boolean existsByDocumentId(String documentId);

}
