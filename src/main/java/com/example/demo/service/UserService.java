package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.User;


public interface UserService {
User save(User u);
User get(Long id);
List<User> getAll();
void delete(Long id);
}