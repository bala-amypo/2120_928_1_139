package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseContentTopicService;

import java.util.List;

public class CourseContentTopicServiceImpl implements CourseContentTopicService {

    // ⚠️ FIELD NAMES USED BY TEST REFLECTION
    private CourseContentTopicRepository repo;
    private CourseRepository courseRepo;

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {

        if (topic.getTopicName() == null || topic.getTopicName().isBlank()) {
            throw new IllegalArgumentException("Topic name required");
        }

        if (topic.getWeightPercentage() == null ||
                topic.getWeightPercentage() < 0 ||
                topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("Weight must be between 0-100");
        }

        Course c = courseRepo.findById(topic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        topic.setCourse(c);
        return repo.save(topic);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        CourseContentTopic existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        return repo.save(existing);
    }

    @Override
    public CourseContentTopic getTopicById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
    }

    @Override
    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return repo.findByCourseId(courseId);
    }
}
