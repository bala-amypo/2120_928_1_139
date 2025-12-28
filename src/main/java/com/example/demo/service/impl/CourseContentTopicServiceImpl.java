package com.example.demo.service.impl;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseContentTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {
    @Autowired
    private CourseContentTopicRepository repo;
    @Autowired
    private CourseRepository courseRepo;

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {
        if (topic.getTopicName() == null || topic.getTopicName().trim().isEmpty()) {
            throw new IllegalArgumentException("Topic name required");
        }
        if (topic.getWeightPercentage() < 0 || topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("Weight percentage must be 0-100");
        }
        if (topic.getCourse() != null) {
            Long cId = topic.getCourse().getId();
            if (cId != null) {
                courseRepo.findById(cId).orElseThrow(() -> new RuntimeException("Course not found"));
            }
        }
        return repo.save(topic);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        Objects.requireNonNull(id, "ID cannot be null");
        CourseContentTopic existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        existing.setTopicName(topic.getTopicName());
        existing.setWeightPercentage(topic.getWeightPercentage());
        return repo.save(existing);
    }

    @Override
    public CourseContentTopic getTopicById(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    @Override
    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        if (courseId != null) {
            courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        }
        return repo.findByCourseId(courseId);
    }
}