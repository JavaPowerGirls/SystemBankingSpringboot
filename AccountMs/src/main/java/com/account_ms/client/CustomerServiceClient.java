package com.account_ms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class CustomerServiceClient {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${customer.service.url:http://localhost:8082}")
    private String customerServiceUrl;

    public boolean clientExists(Long clientId) {
        try {
            String url = customerServiceUrl + "/api/v1/clients/" + clientId;
            restTemplate.getForObject(url, Object.class);
            return true;
       } catch (RestClientException e) {
            return false;
        }
    }
}
