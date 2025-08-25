package com.bankingsystem.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsystem.domain.model.BankAccount;

// Maneja el acceso a los datos de cuentas bancarias 
// Extiende JpaRepository para manejar las operaciones de CRUD
@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {
     
    // Busca una cuenta bancaria usando su número de cuenta
    Optional<BankAccount> findByAccountNumber(String accountNumber); 

}
