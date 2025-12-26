package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferEvaluationResultRepository;

@Service
public class TransferEvaluationResultServiceImpl
        implements TransferEvaluationResultService {

    private final TransferEvaluationResultRepository repository;

    public TransferEvaluationResultServiceImpl(
            TransferEvaluationResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransferEvaluationResult save(TransferEvaluationResult result) {
        return repository.save(result);
    }

    @Override
    public TransferEvaluationResult getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));
    }

    @Override
    public List<TransferEvaluationResult> getAll() {
        return repository.findAll();
    }
}
