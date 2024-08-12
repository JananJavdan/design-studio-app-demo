package com.project.demo.Services;

import com.project.demo.Repositories.CustomerRepository;
import com.project.demo.Repositories.OrderRepository;
import com.project.demo.model.Customer;
import com.project.demo.model.Order;
import com.project.demo.Exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Optional<Customer> login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setAddress(updatedCustomer.getAddress());
                    customer.setRegistrationDate(updatedCustomer.getRegistrationDate());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
    }

    public List<Order> viewOrderStatus(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order placeOrder(Long customerId, Order order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        order.setCustomer(customer);
        return orderRepository.save(order);
    }


    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}

