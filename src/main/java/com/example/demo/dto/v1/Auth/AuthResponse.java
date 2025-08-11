package com.example.demo.dto.v1.Auth;

import com.example.demo.dto.v1.User.UserResponse;

public class AuthResponse {
  private final String tokenType;
  private final String accessToken;
  private final String refreshToken;
  private final UserResponse user;

  public AuthResponse(
      String tokenType, String accessToken, String refreshToken, UserResponse user) {
    this.tokenType = tokenType;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.user = user;
  }

  public String getTokenType() {
    return tokenType;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public UserResponse getUser() {
    return user;
  }
}
