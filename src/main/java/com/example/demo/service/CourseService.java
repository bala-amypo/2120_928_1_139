package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Course;

public interface CourseService {

    Course save(Course course);

    Course getById(Long id);

    List<Course> getAll();

    void delete(Long id);
}
