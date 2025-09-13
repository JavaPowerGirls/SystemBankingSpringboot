package com.customer_ms.service;
import static org.junit.jupiter.api.Assertions.*;

import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import com.customer_ms.service.impl.ClientServiceImpl;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

class ClientServiceTest {

    private ClientRepository repository;
    private ClientServiceImpl clientService;

    @BeforeEach
    void  setUp(){
        repository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(repository);
    }

    @Test
    void testCreateClient_whenNewClient_thenSavedSuccessfully() {

        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Rodriguez")
                .documentId("12345678")
                .email("usuario@gmail.com")
                .build();
        assertNotNull(saved);
        assertEquals("Juan", saved.getFirstName());
        assertEquals(1L, saved.getClientId());
    }
    @Test
    void testCreateClient_whenClientAlreadyExists_thenThrowsException() {
        Client client = Client.builder()
                .firstName("Ignacio")
                .lastName("Gonzales")
                .documentId("87654321")
                .email("ignacio@gmail.com")#
                .build();

        when(repository.existsByDocumentId("87654321")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> clientService.create(client));
        verify(repository, never()).save(any(Client.class));



        }
    }



