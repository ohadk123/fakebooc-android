package com.example.fake_booc.model;

public class LoginResponse {
    private boolean success;
    private String token;
    private String message; // Optional, depending on your API design

    // Constructor, getters, and setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}