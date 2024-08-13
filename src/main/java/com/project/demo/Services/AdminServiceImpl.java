package com.project.demo.Services;

import com.project.demo.Repositories.UserRepository;
import com.project.demo.model.Role;
import com.project.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;


    @Autowired
    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender emailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
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
    public User registerAdmin(User admin) {
        // Ensure that the email is unique
        if (userRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        // Hash the password before saving
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        admin.setRole(Role.ADMIN);

        return userRepository.save(admin);
    }


    @Override
    public List<User> manageUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
// Check if the user exists before deleting
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id " + userId);
        }
        // Delete the user by ID
        userRepository.deleteById(userId);
        System.out.println("User with id " + userId + " has been deleted");

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
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        // Send the email
        emailSender.send(email);

        System.out.println("Email sent to: " + recipientEmail);
    }
}
