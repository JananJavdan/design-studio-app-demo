package com.project.demo.services;

import com.project.demo.models.Admin;
import com.project.demo.models.Customer;
import com.project.demo.repositories.CustomerRepository;
import com.project.demo.repositories.UserRepository;
import com.project.demo.models.Role;
import com.project.demo.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.customerRepository = customerRepository;
    }


    @Override
    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail().toLowerCase());
        if (existingUser.isPresent()) {
            System.out.println("User already exists with email: " + existingUser.get().getEmail());
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        // Encrypt the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate confirmation token
        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        user.setEnabled(false); // Initially, the user is not enabled

        if (user instanceof Customer) {
            user.setRole(Role.CUSTOMER);
        } else if (user instanceof Admin) {
            user.setRole(Role.ADMIN);
        }
        User registeredUser = userRepository.save(user);
        System.out.println("User saved with email: " + registeredUser.getEmail());

        // Send confirmation email
        sendConfirmationEmail(user);

        return registeredUser;
    }
    private void sendConfirmationEmail(User user) {
        String confirmationUrl = "http://localhost:4200/confirm?token=" + user.getConfirmationToken();

        String htmlMsg = "<h2>Bevestig uw registratie</h2>"
                + "<p>Beste " + user.getName() + ",</p>"
                + "<p>Bedankt voor uw registratie bij ons. Om uw account te activeren, klikt u op de volgende link:</p>"
                + "<p><a href=\"" + confirmationUrl + "\">Bevestig uw registratie</a></p>"
                + "<br>"
                + "<p>Met vriendelijke groeten,</p>"
                + "<p><strong>TEXTILE DESIGN APPILICATIE</strong></p>";

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("janan.javdan@outlook.com"); // Zorg ervoor dat dit hetzelfde is als het authenticatieadres
            helper.setTo(user.getEmail());
            helper.setSubject("Bevestig uw registratie");
            helper.setText(htmlMsg, true); // De 'true' parameter betekent dat de e-mail HTML-opmaak ondersteunt.

            mailSender.send(message);
            System.out.println("Bevestigingsmail verzonden naar: " + user.getEmail());
        } catch (MessagingException ex) {
            System.err.println("Kon de e-mail niet verzenden: " + ex.getMessage());
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        user.setId(id);
        // Optionally, you can ensure that certain fields (e.g., email, role) are not inadvertently changed
        user.setPassword(existingUser.get().getPassword()); // Retain original password
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> login(String email, String password) {
        System.out.println("Received login request for email: " + email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getEmail());

            // Gebruik 'password' als het platte tekst wachtwoord dat de gebruiker invoert,
            // en 'user.getPassword()' om het gehashte wachtwoord uit de database te krijgen.
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Password match for user: " + user.getEmail());
                return Optional.of(user);
            } else {
                System.out.println("Password does not match for user: " + user.getEmail());
            }
        } else {
            System.out.println("No user found with email: " + email);
        }

        return Optional.empty();
    }




    @Override
    public Role getRole(Long id) {
        return userRepository.findById(id)
                .map(User::getRole)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> confirmUser(String token) {
        // Find the user by the confirmation token
        Optional<User> userOptional = userRepository.findByConfirmationToken(token);

        // If no user is found with the provided token, return an empty Optional
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }

        // Retrieve the user
        User user = userOptional.get();

        // Enable the user account and clear the confirmation token
        user.setEnabled(true);
        user.setConfirmationToken(null);

        // Save the updated user back to the repository
        userRepository.save(user);

        // Return the confirmed user
        return Optional.of(user);
    }



}
