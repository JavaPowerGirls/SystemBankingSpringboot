package com.customer_ms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class AccountServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${account.service.url:http://localhost:8082}")
    private String accountServiceUrl;

    public boolean hasActiveBankAccounts(Long clientId) {
        try {
            String url = accountServiceUrl + "/api/v1/accounts/clients/" + clientId;
            List<?> accounts = restTemplate.getForObject(url, List.class);
            return accounts != null && !accounts.isEmpty();
        } catch (RestClientException e) {
            return true;
        }
    }
}
