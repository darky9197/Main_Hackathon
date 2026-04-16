package com.backend.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired(required=false)
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, UUID orderId, BigDecimal totalAmount) {
        if(mailSender == null) return;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Order Confirmation - Order #" + orderId);
            message.setText("Thank you for your order! Your total is ₹" + totalAmount + ".\n\nStatus: PENDING");
            mailSender.send(message);
        } catch(Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendStatusUpdate(String toEmail, UUID orderId, String status) {
        if(mailSender == null) return;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Order Update - Order #" + orderId);
            message.setText("Your order is now " + status + ".");
            mailSender.send(message);
        } catch(Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
