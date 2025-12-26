package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;

import java.util.List;
import java.util.Optional;
@Service
public class UniversityServiceImpl implements UniversityService {

    // ⚠️ FIELD NAME USED BY REFLECTION IN TEST
    private UniversityRepository repository;

    @Override
    public University createUniversity(University university) {

        if (university == null || university.getName() == null || university.getName().isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        Optional<University> existing = repository.findByName(university.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("University already exists");
        }

        university.setActive(true);
        return repository.save(university);
    }

    @Override
    public University updateUniversity(Long id, University university) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        if (university.getName() != null) {
            existing.setName(university.getName());
        }

        return repository.save(existing);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    @Override
    public void deactivateUniversity(Long id) {
        University u = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        u.setActive(false);
        repository.save(u);
    }

    @Override
    public List<University> getAll() {
        return repository.findAll();
    }
}
