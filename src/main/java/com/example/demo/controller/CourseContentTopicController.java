package com.example.demo.controller;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class CourseContentTopicController {
    
    @Autowired
    private CourseContentTopicService topicService;
    
    @PostMapping
    public CourseContentTopic create(@RequestBody CourseContentTopic topic) {
        return topicService.createTopic(topic);
    }
    
    @PutMapping("/{id}")
    public CourseContentTopic update(@PathVariable Long id, @RequestBody CourseContentTopic topic) {
        return topicService.updateTopic(id, topic);
    }
    
    @GetMapping("/{id}")
    public CourseContentTopic getById(@PathVariable Long id) {
        return topicService.getTopicById(id);
    }
    
    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getByCourse(@PathVariable Long courseId) {
        return topicService.getTopicsForCourse(courseId);
    }
}