package com.crmbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.crmbackend.entity.User;
import com.crmbackend.security.JwtService;
import com.crmbackend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword())
            );

            User user = userService.getUserByUsername(request.getUsername()); 
            String token = jwtService.generateToken(
            	    new org.springframework.security.core.userdetails.User(
            	        user.getUsername(),
            	        user.getPassword(),
            	        List.of(new SimpleGrantedAuthority(user.getRole().name())) 
            	    )
            	);


            return ResponseEntity.ok().body("Bearer " + token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.isUsernameTaken(user.getUsername());
        return ResponseEntity.ok(userService.createUser(user));
    }
}
