package com.example.myapplication.models.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class User {
    private String username;
    private String displayName;
    private String profileImage;

    public User() {
        this.username = "";
        this.displayName = "";
        this.profileImage = "";
    }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String value) { this.displayName = value; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String value) { this.profileImage = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}
