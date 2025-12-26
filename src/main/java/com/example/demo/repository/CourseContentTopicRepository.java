package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CourseContentTopic;

@Repository
public interface CourseContentTopicRepository
        extends JpaRepository<CourseContentTopic, Long> {

    List<CourseContentTopic> findByCourseId(Long courseId);
}
