package com.customer_ms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Representa un cliente del banco que puede abrir y manejar cuentas bancarias
@Data                       // Genera getters, setters, toString, equals, hashCode
@Builder                    // Permite crear objetos con patrón Builder
@NoArgsConstructor          // Constructor vacío
@AllArgsConstructor         // Constructor con todos los parámetros
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String dni; // e.g. DNI, pasaporte, etc. - OBS.DNI

    @Column(unique = true, nullable = false)
    private String email;
}