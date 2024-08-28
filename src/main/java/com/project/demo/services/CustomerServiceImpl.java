package com.project.demo.services;

import com.project.demo.models.Role;
import com.project.demo.repositories.CustomerRepository;
import com.project.demo.repositories.OrderRepository;
import com.project.demo.models.Customer;
import com.project.demo.models.Order;
import com.project.demo.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, JavaMailSender emailSender) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }
    @Override
    public Customer registerCustomer(Customer customer) {
        // Validate and process customer registration details
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        if (customer.getRegistrationDate() == null) {
            customer.setRegistrationDate(LocalDate.now());
        }
        if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Adres is verplicht.");
        }
        // Set the role of the customer
        customer.setRole(Role.CUSTOMER);
        Customer savedCustomer = customerRepository.save(customer);

        // Bevestigingsmail verzenden na het opslaan van de klant
        sendConfirmationEmail(savedCustomer);

        return savedCustomer;
    
    }

    private void sendConfirmationEmail(Customer savedCustomer) {
        String subject = "Registration Confirmation";
        String message = "Dear " + savedCustomer.getName() + ",\n\nThank you for registering with us.\n\nBest regards,\nYour Company Name";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("janan.javdan@outlook.com");  // Stel het "from"-adres in
        email.setTo(savedCustomer.getEmail());
        email.setSubject(subject);
        email.setText(message);

        emailSender.send(email);

        System.out.println("Confirmation email sent to: " + savedCustomer.getEmail());
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

    @Override
    public Customer createCustomer(Customer customer) {

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email is required.");
        }
        // Validate email format
        if (!customer.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        // Check for duplicate email
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        // Assign default values if needed
        if (customer.getRegistrationDate() == null) {
            customer.setRegistrationDate(LocalDate.now());
        }
        // Hash the password if applicable
        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        // Save the customer to the database
        return customerRepository.save(customer);
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return Optional.empty();
    }

}

