package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.entity.University;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.TransferRuleService;

import java.util.List;

public class TransferRuleServiceImpl implements TransferRuleService {

    // ⚠️ FIELD NAMES USED BY TEST REFLECTION
    private TransferRuleRepository repo;
    private UniversityRepository univRepo;

    @Override
    public TransferRule createRule(TransferRule rule) {

        if (rule.getMinimumOverlapPercentage() == null ||
                rule.getMinimumOverlapPercentage() < 0 ||
                rule.getMinimumOverlapPercentage() > 100) {
            throw new IllegalArgumentException("Overlap must be 0-100");
        }

        if (rule.getCreditHourTolerance() != null && rule.getCreditHourTolerance() < 0) {
            throw new IllegalArgumentException("Credit tolerance must be >= 0");
        }

        University src = univRepo.findById(rule.getSourceUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        University tgt = univRepo.findById(rule.getTargetUniversity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("University not found"));

        rule.setSourceUniversity(src);
        rule.setTargetUniversity(tgt);
        rule.setActive(true);

        return repo.save(rule);
    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {
        TransferRule existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        return repo.save(existing);
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule r = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        r.setActive(false);
        repo.save(r);
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
        return repo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(sourceId, targetId);
    }
}
