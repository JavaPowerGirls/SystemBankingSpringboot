package com.customer_ms.service.impl;

import com.customer_ms.client.AccountClient;
import com.customer_ms.dto.ClientMapper;
import com.customer_ms.dto.ClientRequest;
import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientRequest clientRequest;

    // preparar datos de prueba
    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john@example.com");
        client.setDni("12345678");

        clientRequest = new ClientRequest();
        clientRequest.setFirstName("John");
        clientRequest.setLastName("Doe");
        clientRequest.setEmail("john@example.com");
        clientRequest.setDni("12345678");
    }

    // crear cliente exitosamente
    @Test
    void create_Success() {
        // simular que no existe dni ni email
        when(clientRepository.existsByDni(anyString())).thenReturn(false);
        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
        when(clientMapper.toEntity(any(ClientRequest.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.create(clientRequest);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(clientRepository).existsByDni("12345678");
        verify(clientRepository).existsByEmail("john@example.com");
        verify(clientRepository).save(client);
    }

    // error si dni ya existe
    @Test
    void create_DniAlreadyExists() {
        when(clientRepository.existsByDni(anyString())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> clientService.create(clientRequest));
    }

    // error si email ya existe
    @Test
    void create_EmailAlreadyExists() {
        when(clientRepository.existsByDni(anyString())).thenReturn(false);
        when(clientRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> clientService.create(clientRequest));
    }

    // listar todos los clientes
    @Test
    void listClients() {
        List<Client> clients = Collections.singletonList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.listClients();

        assertEquals(1, result.size());
        verify(clientRepository).findAll();
    }

    // obtener cliente por id
    @Test
    void getClient_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        Client result = clientService.getClient(1L);

        assertEquals(client, result);
        verify(clientRepository).findById(1L);
    }

    // cliente no encontrado
    @Test
    void getClient_NotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> clientService.getClient(1L));
    }

    // eliminar cliente sin cuentas
    @Test
    void deleteClient_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(accountClient.canDeleteClient(anyLong())).thenReturn(true);
        doNothing().when(clientRepository).delete(any(Client.class));

        clientService.deleteClient(1L);

        verify(clientRepository).findById(1L);
        verify(accountClient).canDeleteClient(1L);
        verify(clientRepository).delete(client);
    }

    // no eliminar cliente con cuentas activas
    @Test
    void deleteClient_WithActiveAccounts() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(accountClient.canDeleteClient(anyLong())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> clientService.deleteClient(1L));
    }

    // actualizar cliente exitosamente
    @Test
    void update_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.existsByDniAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(clientRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(false);
        doNothing().when(clientMapper).updateEntityFromDto(any(ClientRequest.class), any(Client.class));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.update(1L, clientRequest);

        assertNotNull(result);
        verify(clientRepository).findById(1L);
        verify(clientRepository).existsByDniAndIdNot("12345678", 1L);
        verify(clientRepository).existsByEmailAndIdNot("john@example.com", 1L);
        verify(clientMapper).updateEntityFromDto(clientRequest, client);
        verify(clientRepository).save(client);
    }

    // error al actualizar con dni duplicado
    @Test
    void update_DniAlreadyUsed() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.existsByDniAndIdNot(anyString(), anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> clientService.update(1L, clientRequest));
    }

    // error al actualizar con email duplicado
    @Test
    void update_EmailAlreadyUsed() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.existsByDniAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(clientRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> clientService.update(1L, clientRequest));
    }
}