package com.example.demo.dto.v1.Auth;

public class AuthResponse {
    private final boolean success;
    private final String message;

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
