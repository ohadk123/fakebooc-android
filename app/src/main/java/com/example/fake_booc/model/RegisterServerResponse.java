package com.example.fake_booc.model;

import java.util.List;

public class RegisterServerResponse {
    private boolean success;
    private List<String> errors; // Assuming errors come as a list

    // Constructor
    public RegisterServerResponse(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }
}
