package com.customer_ms.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountClient {

    private final RestTemplate restTemplate;
    private final String accountServiceUrl;

    public AccountClient(RestTemplate restTemplate,
                        @Value("${account.service.url:http://localhost:8082}") String accountServiceUrl) {
        this.restTemplate = restTemplate;
        this.accountServiceUrl = accountServiceUrl;
    }

    public boolean canDeleteClient(Long clientId) {
        try {
            String url = buildUrl("/api/v1/accounts/client/" + clientId);
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            List<?> accounts = response.getBody() != null ? response.getBody() : Collections.emptyList();
            return accounts.isEmpty();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Cannot verify accounts - Account service not available");
        }
    }

    private String buildUrl(String endpoint) {
        return accountServiceUrl + endpoint;
    }
}