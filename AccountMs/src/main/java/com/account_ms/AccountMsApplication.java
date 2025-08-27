package com.account_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Punto de entrada principal de la aplicación del microservicio de cuentas
@SpringBootApplication
public class AccountMsApplication {

    // Inicia la aplicación Spring Boot del microservicio de cuentas
    public static void main(String[] args) {
        SpringApplication.run(AccountMsApplication.class, args);
    }

}
