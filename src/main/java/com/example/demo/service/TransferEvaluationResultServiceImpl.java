package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferEvaluationResultRepository;

@Service
public class TransferEvaluationResultServiceImpl implements TransferEvaluationResultService {

    private final TransferEvaluationResultRepository repo;

    public TransferEvaluationResultServiceImpl(TransferEvaluationResultRepository repo) {
        this.repo = repo;
    }

    public TransferEvaluationResult save(TransferEvaluationResult r) {
        return repo.save(r);
    }

    public TransferEvaluationResult get(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("TransferEvaluationResult not found with id " + id));
    }

    public List<TransferEvaluationResult> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {

        TransferEvaluationResult result = repo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("TransferEvaluationResult not found with id " + id));

        repo.delete(result);
    }
}
