package com.example.demo;

public class HelloResponse {
    private String message;
    private long timestamp;

    public HelloResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}