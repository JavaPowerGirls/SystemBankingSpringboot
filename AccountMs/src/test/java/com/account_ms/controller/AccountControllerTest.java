package com.account_ms.controller;

import com.account_ms.dto.AccountRequest;
import com.account_ms.dto.AmountRequest;
import com.account_ms.model.AccountType;
import com.account_ms.model.BankAccount;
import com.account_ms.services.AccountService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    // test basico para crear cuenta
    @Test
    void createAccount() throws Exception {
        AccountRequest request = new AccountRequest();
        request.setClientId(1L);
        request.setAccountType(AccountType.CHECKING);

        // mockear cuenta creada
        BankAccount account = new BankAccount(1L, AccountType.CHECKING);
        account.setAccountId(1L);

        when(accountService.createAccount(any(AccountRequest.class))).thenReturn(account);

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(1));
    }

    // obtener todas las cuentas
    @Test
    void getAllAccounts() throws Exception {
        BankAccount account1 = new BankAccount(1L, AccountType.CHECKING);
        BankAccount account2 = new BankAccount(2L, AccountType.SAVINGS);
        List<BankAccount> accounts = Arrays.asList(account1, account2);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // buscar cuenta por numero
    @Test
    void getAccountByAccountNumber() throws Exception {
        BankAccount account = new BankAccount(1L, AccountType.CHECKING);
        account.setAccountNumber("ACC123");

        when(accountService.getAccountByAccountNumber(anyString())).thenReturn(account);

        mockMvc.perform(get("/api/v1/accounts/ACC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("ACC123"));
    }

    // hacer deposito
    @Test
    void deposit() throws Exception {
        AmountRequest request = new AmountRequest();
        request.setAmount(100.0);

        BankAccount account = new BankAccount(1L, AccountType.CHECKING);
        account.setBalance(100.0);

        when(accountService.deposit(anyString(), any(AmountRequest.class))).thenReturn(account);

        mockMvc.perform(put("/api/v1/accounts/ACC123/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    // hacer retiro
    @Test
    void withdraw() throws Exception {
        AmountRequest request = new AmountRequest();
        request.setAmount(50.0);

        BankAccount account = new BankAccount(1L, AccountType.CHECKING);
        account.setBalance(50.0);

        when(accountService.withdraw(anyString(), any(AmountRequest.class))).thenReturn(account);

        mockMvc.perform(put("/api/v1/accounts/ACC123/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(50.0));
    }

    // eliminar cuenta
    @Test
    void deleteAccount() throws Exception {
        doNothing().when(accountService).deleteAccount(anyLong());

        mockMvc.perform(delete("/api/v1/accounts/1"))
                .andExpect(status().isNoContent());
    }

    // obtener cuentas por cliente
    @Test
    void getAccountsByClientId() throws Exception {
        BankAccount account = new BankAccount(1L, AccountType.CHECKING);
        List<BankAccount> accounts = List.of(account);

        when(accountService.getAccountsByClientId(anyLong())).thenReturn(accounts);

        mockMvc.perform(get("/api/v1/accounts/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}