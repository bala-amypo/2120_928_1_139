package com.example.demo.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;


@RestController
@RequestMapping("/api/topics")
public class CourseContentTopicController {


private final CourseContentTopicService service;


public CourseContentTopicController(CourseContentTopicService service) {
this.service = service;
}


@PostMapping
public CourseContentTopic create(@RequestBody CourseContentTopic t) {
return service.save(t);
}


@GetMapping("/{id}")
public CourseContentTopic get(@PathVariable Long id) {
return service.get(id);
}


@GetMapping
public List<CourseContentTopic> getAll() {
return service.getAll();
}


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
service.delete(id);
}
}