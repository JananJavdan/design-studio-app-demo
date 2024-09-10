package com.project.demo.services;

import com.project.demo.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
    Customer createCustomer(Customer customer);
    boolean existsByEmail(String email);
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findCustomerByUsername(String username);
}
