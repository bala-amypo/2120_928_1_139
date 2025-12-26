package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseContentTopicRepository;

@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {

    private final CourseContentTopicRepository repository;

    public CourseContentTopicServiceImpl(CourseContentTopicRepository repository) {
        this.repository = repository;
    }

    @Override
    public CourseContentTopic save(CourseContentTopic topic) {
        return repository.save(topic);
    }

    @Override
    public CourseContentTopic getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
    }

    @Override
    public List<CourseContentTopic> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
