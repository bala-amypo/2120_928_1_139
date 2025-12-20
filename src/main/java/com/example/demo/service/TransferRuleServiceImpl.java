package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferRuleRepository;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    private final TransferRuleRepository repo;

    public TransferRuleServiceImpl(TransferRuleRepository repo) {
        this.repo = repo;
    }

    public TransferRule save(TransferRule r) {
        return repo.save(r);
    }

    // ❗ EXCEPTION ADDED HERE
    public TransferRule get(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("TransferRule not found with id " + id));
    }

    public List<TransferRule> getAll() {
        return repo.findAll();
    }

    // ❗ EXCEPTION ADDED HERE
    public void delete(Long id) {

        TransferRule rule = repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("TransferRule not found with id " + id));

        repo.delete(rule);
    }
}
