package com.crmbackend.impl;

import com.crmbackend.dao.PasswordResetTokenDAO;
import com.crmbackend.dao.UserDAO;
import com.crmbackend.entity.PasswordResetToken;
import com.crmbackend.entity.User;
import com.crmbackend.service.PasswordResetService;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserDAO userDAO;
    private final PasswordResetTokenDAO tokenDAO;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(UserDAO userDAO, PasswordResetTokenDAO tokenDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generateResetToken(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) return "User not found";

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        tokenDAO.saveToken(resetToken);

        
        return token;
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenDAO.getByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Invalid or expired token";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userDAO.updateUser(user);
        tokenDAO.deleteToken(resetToken);

        return "Password successfully updated";
    }
    
    @Override
    public void createToken(User user, String token) {
        // 🧹 Check if old token exists
        PasswordResetToken existing = tokenDAO.getByUserId(user.getId());
        if (existing != null) {
        	  System.out.println("Deleting existing token for user: " + user.getEmail());
            tokenDAO.deleteToken(existing); // 💥 Delete the old token
        }

        // 🔐 Create and save new token
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        System.out.println("Saving new token for user: " + user.getEmail());

        tokenDAO.saveToken(resetToken);
    }



}
