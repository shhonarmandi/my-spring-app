package com.example.demo.service.v1;

import com.example.demo.dto.v1.Auth.AuthRequest;
import com.example.demo.dto.v1.Auth.AuthResponse;
import com.example.demo.dto.v1.User.UserResponse;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final JwtUtil jwtUtil;
  String hardcodedEmail = "user@example.com";
  String hardcodedPassword = "secret123";

  public AuthService(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  public AuthResponse login(AuthRequest request) throws InvalidCredentialsException {
    var isAuthorized =
        request.getEmail().equals(hardcodedEmail)
            && request.getPassword().equals(hardcodedPassword);

    if (!isAuthorized) {
      throw new InvalidCredentialsException();
    }

    var accessToken = jwtUtil.generateAccessToken(request.getEmail());
    var refreshToken = jwtUtil.generateRefreshToken(request.getEmail());

    var user = new UserResponse(8, request.getEmail());
    return new AuthResponse("Bearer", accessToken, refreshToken, user);
  }

  public AuthResponse rotate(String refreshToken) throws InvalidCredentialsException {
    var isAuthorized = jwtUtil.isRefreshToken(refreshToken);

    if (!isAuthorized) {
      throw new InvalidCredentialsException();
    }

    var userEmail = jwtUtil.getSubject(refreshToken);

    var newAccessToken = jwtUtil.generateAccessToken(userEmail);
    var newRefreshToken = jwtUtil.generateRefreshToken(userEmail);

    var user = new UserResponse(8, userEmail);
    return new AuthResponse("Bearer", newAccessToken, newRefreshToken, user);
  }
}
