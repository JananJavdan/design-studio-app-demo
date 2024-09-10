package com.project.demo.controllers;

import com.project.demo.models.EmailRequest;
import com.project.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/send-email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping()
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequest emailRequest) {
        String subject = "Message from " + emailRequest.getName();
        String message = "Message from: " + emailRequest.getEmail() + "\n\n" + emailRequest.getMessage();

        // Call EmailService to send the email
        emailService.sendEmail(emailRequest.getEmail(), subject, message);

        // Return a JSON response instead of plain text
        Map<String, String> response = new HashMap<>();
        response.put("message", "Email sent successfully!");

        return ResponseEntity.ok(response);
    }
}
