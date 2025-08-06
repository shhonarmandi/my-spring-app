package com.example.demo.dto.v1.Auth;

import com.example.demo.dto.v1.User.UserResponse;

public class AuthResponse {
  private final String token;
  private final UserResponse user;

  public AuthResponse(String token, UserResponse user) {
    this.token = token;
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public UserResponse getUser() {
    return user;
  }
}
