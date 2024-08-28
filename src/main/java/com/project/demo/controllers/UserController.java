package com.project.demo.controllers;

import com.project.demo.models.AuthUser;
import com.project.demo.security.JwtUtil;
import com.project.demo.services.CustomUserDetailsService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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




    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully. Please check your email to confirm your registration.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthUser authUser) {
        System.out.println("Received login request for email: " + authUser.getEmail());
        try {
            // Authenticatie van de gebruiker
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authUser.getEmail(), authUser.getPassword())
            );

            // Laad de gebruikersdetails en genereer het JWT-token
            UserDetails userDetails = userDetailsService.loadUserByUsername(authUser.getEmail());
            String jwtToken = jwtUtil.generateToken(userDetails);

            // Retourneer het token in de response body
            return ResponseEntity.ok(jwtToken);

        } catch (BadCredentialsException e) {
            // Retourneer een foutmelding bij onjuiste inloggegevens
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
