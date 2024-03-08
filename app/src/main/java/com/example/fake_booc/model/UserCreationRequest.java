package com.example.fake_booc.model;

public class UserCreationRequest {
    private String username;
    private String profileName;
    private String password;
    private String cPassword;
    private String imageBase64;

    public UserCreationRequest(String username, String profileName, String password,String cPassword, String imageBase64) {
        this.username = username;
        this.profileName = profileName;
        this.password = password;
        this.cPassword = cPassword;
        this.imageBase64 = imageBase64;
    }

    // Getters and Setters
}