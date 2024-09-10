package com.project.demo.controllers;

import com.project.demo.config.AuthResponse;
import com.project.demo.models.AuthUser;
import com.project.demo.repositories.UserRepository;
import com.project.demo.security.JwtUtil;
import com.project.demo.services.CustomUserDetailsService;
import com.project.demo.services.EmailService;
import com.project.demo.services.UserService;
import com.project.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.project.demo.config.ResetPasswordRequest;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            // Controle of de gebruiker al bestaat, validaties, enz.
            userService.registerUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse); // Dit stuurt een 400 met een duidelijke foutmelding terug
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthUser authUser) {
        System.out.println("Received login request for email: " + authUser.getEmail());
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authUser.getEmail(), authUser.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authUser.getEmail());
            String jwtToken = jwtUtil.generateToken(userDetails);


            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam String token) {
        Optional<User> confirmedUser = userService.confirmUser(token);

        if (!confirmedUser.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid or expired confirmation token.");
        }

        return ResponseEntity.ok("User confirmed successfully");
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String token = UUID.randomUUID().toString();
        String email = request.get("email");
        // Zoek de gebruiker op basis van het e-mailadres
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setResetToken(token);
            userRepository.save(user);

            // Stuur een e-mail met de reset-link
            emailService.sendResetPasswordEmail(user);
            return ResponseEntity.ok().body("{\"message\": \"Password reset link sent.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"User with this email does not exist.\"}");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");
        Optional<User> user = userService.getUserByResetToken(token);

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userService.save(user.get());

            // Create a JSON response with a message
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password has been reset successfully.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid token or user not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/user-profile")
    public ResponseEntity<User> getCurrentUserProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = userService.getAllUsers().stream()
                .filter(user -> "CUSTOMER".equals(user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
