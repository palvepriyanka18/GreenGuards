package com.greenguards.components;

public class UserProfile {
    private int userId;
    private String username;
    private String email;
    private String name;
    private String description;
    private byte[] profileImage;

    // Updated constructor to accept all fields
    public UserProfile(int userId, String username, String email, String name, String description, byte[] profileImage) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
    }

    // Getters and Setters (optional)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
