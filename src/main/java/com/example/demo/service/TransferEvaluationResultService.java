package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.TransferEvaluationResult;

public interface TransferEvaluationResultService {

    TransferEvaluationResult save(TransferEvaluationResult result);

    TransferEvaluationResult getById(Long id);

    List<TransferEvaluationResult> getAll();
}
