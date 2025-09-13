package com.customer_ms.service;
import com.customer_ms.model.Client;
import com.customer_ms.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void getClient_WhenExists_ShouldReturnClient() {
        Client client = new Client(1L, "Karen", "Duran", "12345678", "karen@gmail.com");
        when(repository.findById(1L)).thenReturn(Optional.of(client));

        Client result = service.getClient(1L);

        assertNotNull(result);
        assertEquals("Karen", result.getFirstName()); // 👈 usar getFirstName()
        assertEquals("Duran", result.getLastName());  // 👈 usar getLastName()
        verify(repository).findById(1L);
    }

    @Test
    void getClient_WhenNotExists_ShouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.getClient(99L));

        assertEquals("Cliente no encontrado", ex.getMessage());
        verify(repository).findById(99L);
    }

    @Test
    void saveClient_WhenDniExists_ShouldThrowException() {
        Client client = new Client(null, "Juan", "Perez", "12345678", "juan@example.com");
        when(repository.existsByDni("12345678")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.saveClient(client));

        assertEquals("DNI ya registrado", ex.getMessage());
    }

    @Test
    void saveClient_WhenValid_ShouldSaveAndReturnClient() {
        Client client = new Client(null, "Juan", "Perez", "87654321", "juan@example.com");
        when(repository.existsByDni(client.getDni())).thenReturn(false);
        when(repository.existsByEmail(client.getEmail())).thenReturn(false);
        when(repository.save(client)).thenReturn(
                new Client(1L, "Juan", "Perez", "87654321", "juan@example.com")
        );

        Client result = service.saveClient(client);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("Perez", result.getLastName());
        verify(repository).save(client);
    }
}
