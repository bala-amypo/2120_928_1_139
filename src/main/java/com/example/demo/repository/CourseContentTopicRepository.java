package com.example.demo.repository;

import com.example.demo.entity.CourseContentTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseContentTopicRepository extends JpaRepository<CourseContentTopic, Long> {

    List<CourseContentTopic> findByCourseId(Long courseId);
}
