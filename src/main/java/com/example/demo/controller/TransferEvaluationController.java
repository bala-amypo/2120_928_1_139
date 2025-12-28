package com.example.demo.controller;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfer-evaluations")
public class TransferEvaluationController {
    
    @Autowired
    private TransferEvaluationService evaluationService;
    
    @PostMapping("/evaluate/{sourceCourseId}/{targetCourseId}")
    public TransferEvaluationResult evaluate(@PathVariable Long sourceCourseId, @PathVariable Long targetCourseId) {
        return evaluationService.evaluateTransfer(sourceCourseId, targetCourseId);
    }
    
    @GetMapping("/{id}")
    public TransferEvaluationResult getById(@PathVariable Long id) {
        return evaluationService.getEvaluationById(id);
    }
    
    @GetMapping("/course/{courseId}")
    public List<TransferEvaluationResult> getByCourse(@PathVariable Long courseId) {
        return evaluationService.getEvaluationsForCourse(courseId);
    }
}