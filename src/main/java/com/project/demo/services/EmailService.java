package com.project.demo.services;

import com.project.demo.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;


    public void sendResetPasswordEmail(User user) {
        String email = user.getEmail();
        String token = user.getResetToken();
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;

        String message = "<h2>We received your request to reset your password</h2>"
                + "<p>Click the link below to reset your password:</p>"
                + "<a href=\"" + resetUrl + "\">Reset Password</a>"
                + "<p>This link is valid for 24 hours.</p>"
                + "<p>If you did not request a password reset, you can ignore this email.</p>";

        // Send the email
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true); // Set the email content with HTML format
            helper.setTo(email); // Recipient's email address
            helper.setSubject("Password Reset"); // Subject of the email
            helper.setFrom("janan.javdan@outlook.com"); // Sender's email address
            mailSender.send(mimeMessage); // Send the email
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error if necessary
        }
    }

    // Method to send email using data from EmailRequest
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Set the recipient's email, subject, and message body
        message.setTo(toEmail);  // The recipient's email address (from the EmailRequest)
        message.setSubject(subject);  // Subject of the email
        message.setText(body);  // The message content (from EmailRequest message)
        message.setFrom("janan.javdan@outlook.com");  // Set your email address

        try {
            mailSender.send(message);  // Send the email
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}

