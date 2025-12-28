package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repo;
    @Autowired
    private UniversityRepository univRepo;

    @Override
    public Course createCourse(Course course) {
        if (course.getCreditHours() <= 0) {
            throw new IllegalArgumentException("Credit hours must be > 0");
        }
        if (course.getUniversity() != null) {
            Long uId = course.getUniversity().getId();
            if (uId != null) {
                univRepo.findById(uId).orElseThrow(() -> new RuntimeException("University not found"));
                if (repo.findByUniversityIdAndCourseCode(uId, course.getCourseCode()).isPresent()) {
                    throw new IllegalArgumentException("Course code already exists");
                }
            }
        }
        return repo.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Objects.requireNonNull(id, "ID cannot be null");
        Course existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existing.setCourseName(course.getCourseName());
        existing.setCreditHours(course.getCreditHours());
        return repo.save(existing);
    }

    @Override
    public Course getCourseById(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deactivateCourse(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        Course course = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setActive(false);
        repo.save(course);
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return repo.findByUniversityIdAndActiveTrue(universityId);
    }
}