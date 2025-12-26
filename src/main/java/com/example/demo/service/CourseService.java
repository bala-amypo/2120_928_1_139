package com.example.demo.service;

import com.example.demo.entity.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course getCourseById(Long id);

    List<Course> getCoursesByUniversity(Long universityId);
}
