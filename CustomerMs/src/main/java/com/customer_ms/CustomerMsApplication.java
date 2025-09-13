package com.customer_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// Punto de entrada principal de la aplicación del microservicio de clientes
@SpringBootApplication
public class CustomerMsApplication {

    // Inicia la aplicación Spring Boot del microservicio de clientes
    public static void main(String[] args) {
        SpringApplication.run(CustomerMsApplication.class, args);
    }

}
