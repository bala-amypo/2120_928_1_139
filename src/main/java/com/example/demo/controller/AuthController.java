package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request) {
        return "Login successful for: " + request.getEmail();
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest request) {
        return "Registered successfully: " + request.getEmail();
    }
}