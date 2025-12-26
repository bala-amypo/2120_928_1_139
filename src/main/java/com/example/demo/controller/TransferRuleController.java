package com.example.demo.controller;

import com.example.demo.entity.TransferRule;
import com.example.demo.entity.University;
import com.example.demo.service.TransferRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin
public class TransferRuleController {

    private final TransferRuleService service;

    public TransferRuleController(TransferRuleService service) {
        this.service = service;
    }

    @PostMapping
    public TransferRule create(@RequestBody TransferRule rule) {
        University src = new University();
        src.setId(rule.getSourceUniversity().getId());

        University tgt = new University();
        tgt.setId(rule.getTargetUniversity().getId());

        rule.setSourceUniversity(src);
        rule.setTargetUniversity(tgt);

        return service.createRule(rule);
    }

    @GetMapping("/{id}")
    public TransferRule get(@PathVariable Long id) {
        return service.getRuleById(id);
    }

    @GetMapping("/{sourceId}/{targetId}")
    public List<TransferRule> getRules(@PathVariable Long sourceId,
                                       @PathVariable Long targetId) {
        return service.getRulesForUniversities(sourceId, targetId);
    }
}
