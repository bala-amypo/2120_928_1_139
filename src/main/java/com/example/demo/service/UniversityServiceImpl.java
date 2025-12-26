package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.University;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UniversityRepository;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository repository;

    public UniversityServiceImpl(UniversityRepository repository) {
        this.repository = repository;
    }

    @Override
    public University save(University university) {
        return repository.save(university);
    }

    @Override
    public University getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    @Override
    public List<University> getAll() {
        return repository.findAll();
    }
}
