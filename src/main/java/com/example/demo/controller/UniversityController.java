package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;

@RestController
@RequestMapping("/api/universities")
@CrossOrigin(origins = "*")
public class UniversityController {

    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<University> create(@RequestBody University university) {
        return ResponseEntity.ok(service.createUniversity(university));
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUniversityById(id));
    }

    @GetMapping
    public ResponseEntity<List<University>> getAll() {
        return ResponseEntity.ok(service.getAllUniversities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<University> update(
            @PathVariable Long id,
            @RequestBody University university) {
        return ResponseEntity.ok(service.updateUniversity(id, university));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivateUniversity(id);
        return ResponseEntity.noContent().build();
    }
}
