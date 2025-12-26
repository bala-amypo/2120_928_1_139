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

    // ✅ Constructor injection
    public CourseContentTopicController(CourseContentTopicService service) {
        this.service = service;
    }

    // ✅ Create topic
    @PostMapping
    public CourseContentTopic create(@RequestBody CourseContentTopic topic) {

        // avoid sending full Course object from client
        Course c = new Course();
        c.setId(topic.getCourse().getId());
        topic.setCourse(c);

        return service.createTopic(topic);
    }

    // ✅ Get topic by id
    @GetMapping("/{id}")
    public CourseContentTopic getById(@PathVariable Long id) {
        return service.getTopicById(id);
    }

    // ✅ Get all topics for a course
    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getByCourse(@PathVariable Long courseId) {
        return service.getTopicsForCourse(courseId);
    }
}
