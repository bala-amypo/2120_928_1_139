package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.University;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        University u = new University();
        u.setId(course.getUniversity().getId());
        course.setUniversity(u);
        return service.createCourse(course);
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return service.getCourseById(id);
    }

    @GetMapping("/university/{universityId}")
    public List<Course> getByUniversity(@PathVariable Long universityId) {
        return service.getCoursesByUniversity(universityId);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateCourse(id);
    }
}
