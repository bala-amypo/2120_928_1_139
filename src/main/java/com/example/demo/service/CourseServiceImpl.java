package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Course;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;

    public CourseServiceImpl(CourseRepository repo) {
        this.repo = repo;
    }

    public Course save(Course c) {
        return repo.save(c);
    }

    public Course get(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Course not found with id " + id));
    }

    public List<Course> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {

        Course course = repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Course not found with id " + id));

        repo.delete(course);
    }
}
