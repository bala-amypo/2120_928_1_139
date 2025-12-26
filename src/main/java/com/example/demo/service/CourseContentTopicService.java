package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.CourseContentTopic;

public interface CourseContentTopicService {

    CourseContentTopic save(CourseContentTopic topic);

    CourseContentTopic getById(Long id);

    List<CourseContentTopic> getAll();

    void delete(Long id);
}
