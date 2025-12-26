package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferRuleRepository;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    private final TransferRuleRepository repository;

    public TransferRuleServiceImpl(TransferRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransferRule save(TransferRule rule) {
        return repository.save(rule);
    }

    @Override
    public TransferRule getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public List<TransferRule> getAll() {
        return repository.findAll();
    }
}
