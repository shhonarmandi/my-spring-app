package com.example.demo.service.v1;

import com.example.demo.dto.v1.Auth.AuthRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  String hardcodedEmail = "user@example.com";
  String hardcodedPassword = "secret123";

  public boolean login(AuthRequest request) {
    return request.getEmail().equals(hardcodedEmail)
        && request.getPassword().equals(hardcodedPassword);
  }
}
