package com.example.demo.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;


@RestController
@RequestMapping("/api/transfer-rules")
public class TransferRuleController {


private final TransferRuleService service;


public TransferRuleController(TransferRuleService service) {
this.service = service;
}


@PostMapping
public TransferRule create(@RequestBody TransferRule r) {
return service.save(r);
}


@GetMapping("/{id}")
public TransferRule get(@PathVariable Long id) {
return service.get(id);
}


@GetMapping
public List<TransferRule> getAll() {
return service.getAll();
}


@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
service.delete(id);
}
}