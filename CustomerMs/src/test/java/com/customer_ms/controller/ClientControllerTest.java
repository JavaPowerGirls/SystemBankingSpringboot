package com.customer_ms.controller;


import com.customer_ms.model.Client;
import com.customer_ms.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
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

    @Test
    void testCreateClient() throws Exception {
        Client client = new Client();
        client.setClientId(1L);
        client.setFirstName("Andrea");
        client.setLastName("Ortiz");
        client.setDocumentId("12345345");
        client.setEmail("andrea@gmail.com");

        when(clientService.create(any(Client.class))).thenReturn(client);
        mockMvc.perform((post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writterValueAsString(client)));
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"));

        verify(clientService, times(1)).create(any(Client.class));
    }
    @Test
    void testViewClients() throws Exception {
        Client client = new Client();
        client.setClientId(1L);
        client.setFirstName("Andrea");
        client.setLastName("Ortiz");
        client.setDocumentId("12345345");
        client.setEmail("andrea@gmail.com");

        when(clientService.listClients()).thenReturn(client);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Andrea"));
        verify(clientService,times(1)).listClients();

    }


}
