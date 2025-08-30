package com.customer_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.customer_ms.model.Client;

// Maneja el acceso a datos de clientes
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByDocumentId(String dni);
}
