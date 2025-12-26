package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // ⭐ REQUIRED so Spring creates bean
public class CourseContentTopicServiceImpl
        implements CourseContentTopicService {

    // ⚠️ field names MUST stay like this (used by tests)
    private final CourseContentTopicRepository repo;
    private final CourseRepository courseRepo;

    // ✅ Constructor injection
    public CourseContentTopicServiceImpl(
            CourseContentTopicRepository repo,
            CourseRepository courseRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
    }

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {

        if (topic.getTopicName() == null || topic.getTopicName().isBlank()) {
            throw new IllegalArgumentException("Topic name required");
        }

        if (topic.getWeightPercentage() == null
                || topic.getWeightPercentage() < 0
                || topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("Weight must be between 0-100");
        }

        Course course = courseRepo.findById(topic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        topic.setCourse(course);
        return repo.save(topic);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {

        CourseContentTopic existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        existing.setTopicName(topic.getTopicName());
        existing.setWeightPercentage(topic.getWeightPercentage());

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
