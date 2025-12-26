package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.University;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepo;
    private UniversityRepository universityRepo;

    public CourseServiceImpl() {
    }

    public CourseServiceImpl(
            CourseRepository courseRepo,
            UniversityRepository universityRepo) {
        this.courseRepo = courseRepo;
        this.universityRepo = universityRepo;
    }

    @Override
    public Course createCourse(Course course) {

        if (course.getCourseCode() == null || course.getCourseCode().isBlank()) {
            throw new IllegalArgumentException("Course code required");
        }

        if (course.getCourseName() == null || course.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name required");
        }

        University uni = universityRepo.findById(course.getUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        course.setUniversity(uni);
        course.setActive(true);

        return courseRepo.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        universityRepo.findById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        return courseRepo.findByUniversityIdAndActiveTrue(universityId);
    }

    public Course deactivateCourse(long id) {
        Course course = getCourseById(id);
        course.setActive(false);
        return courseRepo.save(course);
    }

    public Course updateCourse(long id, Course updated) {

        Course existing = getCourseById(id);

        if (updated.getCourseName() != null) {
            existing.setCourseName(updated.getCourseName());
        }

        if (updated.getCreditHours() != null) {
            existing.setCreditHours(updated.getCreditHours());
        }

        return courseRepo.save(existing);
    }
}
