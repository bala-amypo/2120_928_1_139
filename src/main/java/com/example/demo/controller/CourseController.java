package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return ResponseEntity.ok(service.createCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCourseById(id));
    }

    @GetMapping("/university/{universityId}")
    public ResponseEntity<List<Course>> getByUniversity(
            @PathVariable Long universityId) {
        return ResponseEntity.ok(service.getCoursesByUniversity(universityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(
            @PathVariable Long id,
            @RequestBody Course course) {
        return ResponseEntity.ok(service.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivateCourse(id);
        return ResponseEntity.noContent().build();
    }
}
