package com.example.demo.dto.v1.User;

public class UserResponse {
  private final int id;
  private final String email;

  public UserResponse(int id, String email) {
    this.id = id;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
