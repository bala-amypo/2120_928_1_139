package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.University;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    // ⚠️ FIELD NAMES USED BY REFLECTION
    private CourseRepository repo;
    private UniversityRepository univRepo;

    @Override
    public Course createCourse(Course course) {

        if (course.getCreditHours() <= 0) {
            throw new IllegalArgumentException("Credit hours must be > 0");
        }

        Long universityId = course.getUniversity().getId();
        University u = univRepo.findById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        repo.findByUniversityIdAndCourseCode(universityId, course.getCourseCode())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Duplicate course code");
                });

        course.setUniversity(u);
        course.setActive(true);
        return repo.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return repo.save(existing);
    }

    @Override
    public Course getCourseById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    @Override
    public void deactivateCourse(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        c.setActive(false);
        repo.save(c);
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return repo.findByUniversityIdAndActiveTrue(universityId);
    }
}
