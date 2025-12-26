package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "*")
public class CourseContentTopicController {

    private final CourseContentTopicService service;

    public CourseContentTopicController(CourseContentTopicService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourseContentTopic> create(
            @RequestBody CourseContentTopic topic) {
        return ResponseEntity.ok(service.createTopic(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseContentTopic> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTopicById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseContentTopic>> getByCourse(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(service.getTopicsForCourse(courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseContentTopic> update(
            @PathVariable Long id,
            @RequestBody CourseContentTopic topic) {
        return ResponseEntity.ok(service.updateTopic(id, topic));
    }
}
