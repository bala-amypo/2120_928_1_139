package com.example.demo.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;


@Service
public class UniversityServiceImpl implements UniversityService {


private final UniversityRepository repo;
public UniversityServiceImpl(UniversityRepository repo) { this.repo = repo; }


public University save(University u) { return repo.save(u); }
public University get(Long id) { return repo.findById(id).orElseThrow(); }
public List<University> getAll() { return repo.findAll(); }
public void delete(Long id) { repo.deleteById(id); }
}