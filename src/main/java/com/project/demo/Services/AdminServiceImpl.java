package com.project.demo.Services;

import com.project.demo.Repositories.AdminRepository;
import com.project.demo.Repositories.OrderRepository;
import com.project.demo.Repositories.UserRepository;
import com.project.demo.model.Admin;
import com.project.demo.model.Order;
import com.project.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Autowired
    public AdminServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));

        // Vergelijk het ingevoerde wachtwoord met het opgeslagen wachtwoord (bijvoorbeeld door BCrypt te gebruiken)
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        // Als de wachtwoorden overeenkomen, beschouw de gebruiker als ingelogd (dit is een simplificatie)
        System.out.println("Admin " + email + " logged in successfully");
    }


    @Override
    public List<User> manageUsers() {
        return List.of();
    }

    @Override
    public List<Order> manageOrders() {
        return orderRepository.findAll();
    }

    @Override
        public void updateProductCatalog (Long productId, String productDetails){
            // Je zou hier de logica implementeren om een product op te halen en bij te werken
            // Bijvoorbeeld:
            // Product product = productRepository.findById(productId)
            //        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
            // product.setDetails(productDetails);
            // productRepository.save(product);
            System.out.println("Product with id " + productId + " updated with details: " + productDetails);
        }

    @Override
    public void sendConfirmationEmail(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        sendEmail(user.getEmail(), "Order Confirmation", message);
    }

    @Override
    public void sendNotificationEmail(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        sendEmail(user.getEmail(), "Notification", message);
    }

    

    private void sendEmail(String recipientEmail, String subject, String message) {
        // Dummy implementatie voor het verzenden van e-mails
        // In een echte applicatie zou je hier gebruik maken van JavaMail API of een andere e-mailservice

        System.out.println("Sending email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);

        // Hier zou je bijvoorbeeld de JavaMail API aanroepen om daadwerkelijk een e-mail te sturen
    }
}
