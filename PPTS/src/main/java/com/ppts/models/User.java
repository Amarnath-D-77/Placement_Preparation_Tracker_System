package com.ppts.models;

public class User {
    private int id;
    private String username;
    private String password;
    private int currentRating;
    private int targetRating;

    public User(int id, String username, String password, int currentRating, int targetRating) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentRating = currentRating;
        this.targetRating = targetRating;
    }

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getCurrentRating() { return currentRating; }
    public void setCurrentRating(int currentRating) { this.currentRating = currentRating; }
    
    public int getTargetRating() { return targetRating; }
    public void setTargetRating(int targetRating) { this.targetRating = targetRating; }
}
