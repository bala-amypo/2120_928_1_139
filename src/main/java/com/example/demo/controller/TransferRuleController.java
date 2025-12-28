package com.example.demo.controller;

import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfer-rules")
public class TransferRuleController {
    
    @Autowired
    private TransferRuleService ruleService;
    
    @PostMapping
    public TransferRule create(@RequestBody TransferRule rule) {
        return ruleService.createRule(rule);
    }
    
    @PutMapping("/{id}")
    public TransferRule update(@PathVariable Long id, @RequestBody TransferRule rule) {
        return ruleService.updateRule(id, rule);
    }
    
    @GetMapping("/{id}")
    public TransferRule getById(@PathVariable Long id) {
        return ruleService.getRuleById(id);
    }
    
    @GetMapping("/pair/{sourceId}/{targetId}")
    public List<TransferRule> getRulesPair(@PathVariable Long sourceId, @PathVariable Long targetId) {
        return ruleService.getRulesForUniversities(sourceId, targetId);
    }
    
    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        ruleService.deactivateRule(id);
    }
}