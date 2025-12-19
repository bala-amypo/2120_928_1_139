package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.TransferEvaluationResult;


public interface TransferEvaluationResultService {
TransferEvaluationResult save(TransferEvaluationResult r);
TransferEvaluationResult get(Long id);
List<TransferEvaluationResult> getAll();
void delete(Long id);
}