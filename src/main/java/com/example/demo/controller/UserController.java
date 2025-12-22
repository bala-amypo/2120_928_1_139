package com.example.demo.controller;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // ✅ Enables CORS for all domains (adjust in production)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ✅ Create a new user
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        User savedUser = service.save(u);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = service.get(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // ✅ Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // ✅ Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
