package com.crmbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.crmbackend.entity.User;
import com.crmbackend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        String result = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        String result = userService.updateUser(id, user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        String result = userService.deleteUser(id);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        String username = authentication.getName(); // gets username from JWT
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user); // you can return a DTO instead
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateMyProfile(Authentication authentication, @RequestBody User updatedUser) {
        String username = authentication.getName();
        String message = userService.updateUserByUsername(username, updatedUser);
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("/by-email/{email}")
    @PreAuthorize("#email == authentication.principal.id or hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
    	User byemail = userService.getUserByEmail(email);
    	return ResponseEntity.ok(byemail);
    	
    }

    
    

}
