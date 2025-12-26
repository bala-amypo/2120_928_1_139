package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String role;

    // REQUIRED no-args constructor
    public AuthResponse() {
    }

    // Convenience constructor
    public AuthResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
