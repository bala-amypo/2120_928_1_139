package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "*")
public class TransferRuleController {

    private final TransferRuleService service;

    public TransferRuleController(TransferRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferRule> create(@RequestBody TransferRule rule) {
        return ResponseEntity.ok(service.createRule(rule));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferRule> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRuleById(id));
    }

    @GetMapping("/between")
    public ResponseEntity<List<TransferRule>> getRules(
            @RequestParam Long sourceUniversityId,
            @RequestParam Long targetUniversityId) {
        return ResponseEntity.ok(
                service.getRulesForUniversities(sourceUniversityId, targetUniversityId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransferRule> update(
            @PathVariable Long id,
            @RequestBody TransferRule rule) {
        return ResponseEntity.ok(service.updateRule(id, rule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivateRule(id);
        return ResponseEntity.noContent().build();
    }
}
