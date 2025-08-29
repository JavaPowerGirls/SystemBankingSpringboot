package com.customer_ms.service;
import com.demo.example.customer_ms.model.Customer;
import java.util.List;


// Define las operaciones para gestionar los clientes del banco
public interface ClientService {
        public List<Customer> listCustomers();
        public Customer getCustomer(int id);
        public boolean deleteCustomer(int id);
}
