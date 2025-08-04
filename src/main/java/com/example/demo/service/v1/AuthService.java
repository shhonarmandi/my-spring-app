package com.example.demo.service.v1;

import com.example.demo.dto.v1.Auth.AuthRequest;
import com.example.demo.dto.v1.Auth.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    String hardcodedEmail = "user@example.com";
    String hardcodedPassword = "secret123";

    public AuthResponse login(AuthRequest request) {
        if (request.getEmail().equals(hardcodedEmail) && request.getPassword().equals(hardcodedPassword)) {
            return new AuthResponse(true,"Login successful!");
        } else {
            return new AuthResponse(false, "Invalid credentials");
        }
    }
}
