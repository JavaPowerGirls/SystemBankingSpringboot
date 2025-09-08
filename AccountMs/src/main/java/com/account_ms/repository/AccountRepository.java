package com.account_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.account_ms.model.BankAccount;
import java.util.List;

// Maneja el acceso a los datos de cuentas bancarias 
// Extiende JpaRepository para manejar las operaciones de CRUD
@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {
    
    // Busca todas las cuentas de un cliente específico
    List<BankAccount> findByClientId(Long clientId);
}
