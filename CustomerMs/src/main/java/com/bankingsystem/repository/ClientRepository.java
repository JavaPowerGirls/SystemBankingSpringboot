package com.bankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsystem.model.Client;

// Maneja el acceso a datos de clientes
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
 

}
