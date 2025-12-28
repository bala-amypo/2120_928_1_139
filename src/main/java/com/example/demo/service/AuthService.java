package com.example.demo.service;

import com.example.demo.entity.User;

public interface AuthService {
    User register(String email, String password);
    String login(String email, String password);
}