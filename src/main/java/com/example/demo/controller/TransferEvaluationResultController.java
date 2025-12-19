package com.example.demo.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationResultService;


@RestController
@RequestMapping("/api/transfer-results")
public class TransferEvaluationResultController {


private final TransferEvaluationResultService service;


public TransferEvaluationResultController(TransferEvaluationResultService service) {
this.service = service;
}


@PostMapping
public TransferEvaluationResult create(@RequestBody TransferEvaluationResult r) {
return service.save(r);
}


@GetMapping("/{id}")
public TransferEvaluationResult get(@PathVariable Long id) {
return service.get(id);
}


@GetMapping
public List<TransferEvaluationResult> getAll() {
return service.getAll();
}


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
service.delete(id);
}
}