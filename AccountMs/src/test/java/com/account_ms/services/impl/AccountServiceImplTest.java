package com.account_ms.services.impl;

import com.account_ms.client.CustomerClient;
import com.account_ms.dto.AccountRequest;
import com.account_ms.dto.AmountRequest;
import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;
import com.account_ms.repository.AccountRepository;
import com.account_ms.rules.WithdrawalRule;
import com.account_ms.rules.WithdrawalRuleFactory;
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
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private WithdrawalRuleFactory withdrawalRuleFactory;

    @Mock
    private WithdrawalRule withdrawalRule;

    @InjectMocks
    private AccountServiceImpl accountService;

    private BankAccount bankAccount;
    private AccountRequest accountRequest;
    private AmountRequest amountRequest;

    // setup inicial para tests
    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(1L, AccountType.CHECKING);
        bankAccount.setAccountId(1L);
        bankAccount.setAccountNumber("ACC123");
        bankAccount.setBalance(1000.0);

        accountRequest = new AccountRequest();
        accountRequest.setClientId(1L);
        accountRequest.setAccountType(AccountType.CHECKING);

        amountRequest = new AmountRequest();
        amountRequest.setAmount(100.0);
    }

    // crear cuenta exitosamente
    @Test
    void createAccount_Success() {
        when(customerClient.existsById(anyLong())).thenReturn(false);
        when(accountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccount result = accountService.createAccount(accountRequest);

        assertNotNull(result);
        verify(customerClient).existsById(1L);
        verify(accountRepository).save(any(BankAccount.class));
    }

    // falla cuando cliente no existe
    @Test
    void createAccount_ClientNotExists() {
        when(customerClient.existsById(anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> accountService.createAccount(accountRequest));
    }

    // listar todas las cuentas
    @Test
    void getAllAccounts() {
        List<BankAccount> accounts = Collections.singletonList(bankAccount);
        when(accountRepository.findAll()).thenReturn(accounts);

        List<BankAccount> result = accountService.getAllAccounts();

        assertEquals(1, result.size());
        verify(accountRepository).findAll();
    }

    // buscar cuenta por numero exitoso
    @Test
    void getAccountByAccountNumber_Success() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(bankAccount));

        BankAccount result = accountService.getAccountByAccountNumber("ACC123");

        assertEquals(bankAccount, result);
        verify(accountRepository).findByAccountNumber("ACC123");
    }

    // cuenta no encontrada por numero
    @Test
    void getAccountByAccountNumber_NotFound() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> accountService.getAccountByAccountNumber("ACC123"));
    }

    // hacer deposito en cuenta
    @Test
    void deposit() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(bankAccount));
        when(accountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccount result = accountService.deposit("ACC123", amountRequest);

        assertNotNull(result);
        verify(accountRepository).findByAccountNumber("ACC123");
        verify(accountRepository).save(bankAccount);
    }

    // hacer retiro con validaciones
    @Test
    void withdraw() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(bankAccount));
        when(withdrawalRuleFactory.getRule(any(AccountType.class))).thenReturn(withdrawalRule);
        doNothing().when(withdrawalRule).validate(anyDouble(), anyDouble());
        when(accountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccount result = accountService.withdraw("ACC123", amountRequest);

        assertNotNull(result);
        verify(withdrawalRuleFactory).getRule(AccountType.CHECKING);
        verify(withdrawalRule).validate(anyDouble(), anyDouble());
        verify(accountRepository).save(bankAccount);
    }

    // eliminar cuenta existente
    @Test
    void deleteAccount_Success() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));
        doNothing().when(accountRepository).deleteById(anyLong());

        accountService.deleteAccount(1L);

        verify(accountRepository).findById(1L);
        verify(accountRepository).deleteById(1L);
    }

    // error al eliminar cuenta inexistente
    @Test
    void deleteAccount_NotFound() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> accountService.deleteAccount(1L));
    }

    // obtener cuentas de cliente valido
    @Test
    void getAccountsByClientId_Success() {
        List<BankAccount> accounts = Collections.singletonList(bankAccount);
        when(customerClient.existsById(anyLong())).thenReturn(false);
        when(accountRepository.findByClientId(anyLong())).thenReturn(accounts);

        List<BankAccount> result = accountService.getAccountsByClientId(1L);

        assertEquals(1, result.size());
        verify(customerClient).existsById(1L);
        verify(accountRepository).findByClientId(1L);
    }

    // cliente no existe al buscar cuentas
    @Test
    void getAccountsByClientId_ClientNotExists() {
        when(customerClient.existsById(anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> accountService.getAccountsByClientId(1L));
    }
}