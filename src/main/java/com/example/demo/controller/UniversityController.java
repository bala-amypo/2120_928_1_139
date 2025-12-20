package com.example.demo.controller;


import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;


@RestController
@RequestMapping("/api/universities")
public class UniversityController {


private final UniversityService service;
public UniversityController(UniversityService service) { this.service = service; }


@PostMapping
public University create(@Valid @RequestBody University u) { return service.save(u); }


@GetMapping("/{id}")
public University get(@PathVariable Long id) { return service.get(id); }


@GetMapping
public List<University> all() { return service.getAll(); }


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) { service.delete(id); }
}