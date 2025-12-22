package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    public User save(User u) {
        return repo.save(u);
    }

    public User get(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("User not found with id " + id));
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("User not found with id " + id));

        repo.delete(user);
    }
}
