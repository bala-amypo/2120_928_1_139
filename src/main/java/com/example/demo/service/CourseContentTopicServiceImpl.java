package com.example.demo.service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;


@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {


private final CourseContentTopicRepository repo;


public CourseContentTopicServiceImpl(CourseContentTopicRepository repo) {
this.repo = repo;
}


public CourseContentTopic save(CourseContentTopic t) { return repo.save(t); }
public CourseContentTopic get(Long id) { return repo.findById(id).orElseThrow(); }
public List<CourseContentTopic> getAll() { return repo.findAll(); }
public void delete(Long id) { repo.deleteById(id); }
}