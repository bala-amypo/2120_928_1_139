package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin
public class CourseContentTopicController {

    private final CourseContentTopicService service;

    public CourseContentTopicController(CourseContentTopicService service) {
        this.service = service;
    }

    @PostMapping
    public CourseContentTopic create(@RequestBody CourseContentTopic topic) {
        Course c = new Course();
        c.setId(topic.getCourse().getId());
        topic.setCourse(c);
        return service.createTopic(topic);
    }

    @GetMapping("/{id}")
    public CourseContentTopic get(@PathVariable Long id) {
        return service.getTopicById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getByCourse(@PathVariable Long courseId) {
        return service.getTopicsForCourse(courseId);
    }
}
