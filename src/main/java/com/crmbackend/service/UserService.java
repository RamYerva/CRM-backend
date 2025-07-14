package com.crmbackend.service;

import java.util.List;
import com.crmbackend.entity.User;

public interface UserService {
    String createUser(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    String updateUser(int id, User user);
    String deleteUser(int id);
    boolean isUsernameTaken(String username);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    String updateUserByUsername(String username, User updatedUser);


}
