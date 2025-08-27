package com.bankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingsystem.model.BankAccount;

// Maneja el acceso a los datos de cuentas bancarias 
// Extiende JpaRepository para manejar las operaciones de CRUD
@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Long> {
      
}
