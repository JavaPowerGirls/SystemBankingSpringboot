package com.customer_ms.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//esto es lo que se pide al cliente, no se le pide id
@Data
public class ClientRequest {
    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "dni is required")
    private String dni;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;
}
