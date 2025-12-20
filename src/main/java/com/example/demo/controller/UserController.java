package com.example.demo.controller;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {


private final UserService service;


public UserController(UserService service) {
this.service = service;
}


@PostMapping
public User create(@Valid @RequestBody User u) {
return service.save(u);
}


@GetMapping("/{id}")
public User get(@PathVariable Long id) {
return service.get(id);
}


@GetMapping
public List<User> getAll() {
return service.getAll();
}


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
service.delete(id);
}
}