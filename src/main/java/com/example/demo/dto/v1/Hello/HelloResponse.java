package com.example.demo.dto.v1.Hello;

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