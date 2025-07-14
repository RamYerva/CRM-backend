package com.crmbackend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crmbackend.dao.UserDAO;
import com.crmbackend.entity.User;
import com.crmbackend.exception.ResourceAlreadyExistsException;
import com.crmbackend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO,PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    
    

    @Override
    public String createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.createUser(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public String updateUser(int id, User user) {
        return userDAO.updateUser(id, user);
    }

    @Override
    public String deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        boolean exists = userDAO.isUsernameTaken(username);
        if (exists) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }




	@Override
	public String updateUserByUsername(String username, User updatedUser) {
		return userDAO.updateUserByUsername(username, updatedUser);
	}




	@Override
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

}
