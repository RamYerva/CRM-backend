package com.crmbackend.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crmbackend.service.*;

@Controller
@RequestMapping("/api/users")
public class ForgotPasswordController {

    private final EmailService emailService;
    private final PasswordResetService tokenService;
    private final UserService userService;
    
    

    public ForgotPasswordController(EmailService emailService, PasswordResetService tokenService,
			UserService userService) {
		super();
		this.emailService = emailService;
		this.tokenService = tokenService;
		this.userService = userService;
	}



    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = tokenService.generateResetToken(email); // centralized token + save
        emailService.sendPasswordResetEmail(email, token);
        return ResponseEntity.ok("Reset link sent to your email.");
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        String result = tokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/reset-password2")
    @ResponseBody
    public String resetPage(@RequestParam String token) {
        return "<html><body>" +
               "<h2>Reset Your Password</h2>" +
               "<form action='/api/users/reset-password-form' method='post'>" +
               "<input type='hidden' name='token' value='" + token + "'/>" +
               "New Password: <input type='password' name='newPassword'/><br><br>" +
               "Confirm Password: <input type='password' name='newPassword'/><br><br>"+
               "<button type='submit'>Reset Password</button>" +
               "</form>" +
               "</body></html>";
    }

    
    @PostMapping("/reset-password-form")
    public ResponseEntity<String> resetPasswordFromForm(
            @RequestParam String token,
            @RequestParam String newPassword) {

        String result = tokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok("<html><body><h3>" + result + "</h3></body></html>");
    }





}
