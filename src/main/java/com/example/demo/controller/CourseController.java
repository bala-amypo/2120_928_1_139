package com.example.demo.controller;


import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;


@RestController
@RequestMapping("/api/courses")
public class CourseController {


private final CourseService service;


public CourseController(CourseService service) {
this.service = service;
}


@PostMapping
public Course create(@RequestBody Course c) {
return service.save(c);
}


@GetMapping("/{id}")
public Course get(@PathVariable Long id) {
return service.get(id);
}


@GetMapping
public List<Course> getAll() {
return service.getAll();
}


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
service.delete(id);
}
}