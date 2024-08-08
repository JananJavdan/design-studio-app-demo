package com.project.demo.Services;

import com.project.demo.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
}
