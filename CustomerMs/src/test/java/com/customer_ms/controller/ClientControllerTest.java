package com.customer_ms.controller;

import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    // crear cliente nuevo
    @Test
    void createClient() throws Exception {
        ClientRequest request = new ClientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setDni("12345678");

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john@example.com");

        when(clientService.create(any(ClientRequest.class))).thenReturn(client);

        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    // obtener todos los clientes
    @Test
    void getAllClients() throws Exception {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setFirstName("John");
        client1.setLastName("Doe");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setFirstName("Jane");
        client2.setLastName("Doe");

        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.listClients()).thenReturn(clients);

        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // actualizar datos de cliente
    @Test
    void updateClient() throws Exception {
        ClientRequest request = new ClientRequest();
        request.setFirstName("Updated");
        request.setLastName("Name");
        request.setEmail("updated@example.com");
        request.setDni("87654321");

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setFirstName("Updated");
        updatedClient.setLastName("Name");

        when(clientService.update(anyLong(), any(ClientRequest.class))).thenReturn(updatedClient);

        mockMvc.perform(put("/api/v1/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    // ver cliente por id
    @Test
    void viewClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");

        when(clientService.getClient(anyLong())).thenReturn(client);

        mockMvc.perform(get("/api/v1/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    // eliminar cliente
    @Test
    void deleteClient() throws Exception {
        doNothing().when(clientService).deleteClient(anyLong());

        mockMvc.perform(delete("/api/v1/clients/1"))
                .andExpect(status().isNoContent());
    }
}