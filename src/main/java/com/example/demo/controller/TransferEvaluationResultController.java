package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationService;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(origins = "*")
public class TransferEvaluationResultController {

    private final TransferEvaluationService service;

    public TransferEvaluationResultController(TransferEvaluationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferEvaluationResult> evaluate(
            @RequestParam Long sourceCourseId,
            @RequestParam Long targetCourseId) {
        return ResponseEntity.ok(
                service.evaluateTransfer(sourceCourseId, targetCourseId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferEvaluationResult> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getEvaluationById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<TransferEvaluationResult>> getForCourse(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(service.getEvaluationsForCourse(courseId));
    }
}
