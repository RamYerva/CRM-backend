package com.crmbackend.service;


import com.crmbackend.entity.User;

public interface PasswordResetService {
    String generateResetToken(String username);
    String resetPassword(String token, String newPassword);
    void createToken(User user, String token);
   
}

