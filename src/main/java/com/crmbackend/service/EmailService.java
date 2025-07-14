package com.crmbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/api/users/reset-password2?token=" + token;
        String message = "Hi,\n\nTo reset your password, click the link below:\n" + resetLink +
                         "\n\nIf you didn't request this, ignore this email.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("ramanjaneyareddyyerva@gmail.com");

        mailSender.send(email);
    }

}

