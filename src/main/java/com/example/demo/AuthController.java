package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String hardcodedEmail = "user@example.com";
        String hardcodedPassword = "secret123";

        if (request.getEmail().equals(hardcodedEmail) && request.getPassword().equals(hardcodedPassword)) {
            return ResponseEntity.ok(new LoginResponse("Login successful!"));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials"));
        }
    }
}
