package com.example.demo.exception;

import com.example.demo.dto.Target;

public class InvalidCredentialsException extends RuntimeException {
  private final String code;
  private final String description;
  private final Target target;

  public InvalidCredentialsException(String code, String description) {
    this.code = code;
    this.description = description;
    this.target = null;
  }

  public InvalidCredentialsException(String code, String description, Target target) {
    this.code = code;
    this.description = description;
    this.target = target;
  }

  public static InvalidCredentialsException forHeaderEmailAndPassword() {
    return new InvalidCredentialsException("invalid_credentials", "Authentication failed.");
  }

  public static InvalidCredentialsException forHeaderToken(String headerName) {
    return new InvalidCredentialsException(
        "invalid_token", "The token is invalid.", new Target("header", headerName));
  }

  public static InvalidCredentialsException forCookieToken(String cookieName) {
    return new InvalidCredentialsException(
        "invalid_token", "The token is invalid.", new Target("cookie", cookieName));
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public Target getTarget() {
    return target;
  }
}
