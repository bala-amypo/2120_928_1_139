package com.example.demo.service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepository;


@Service
public class TransferRuleServiceImpl implements TransferRuleService {


private final TransferRuleRepository repo;


public TransferRuleServiceImpl(TransferRuleRepository repo) {
this.repo = repo;
}


public TransferRule save(TransferRule r) { return repo.save(r); }
public TransferRule get(Long id) { return repo.findById(id).orElseThrow(); }
public List<TransferRule> getAll() { return repo.findAll(); }
public void delete(Long id) { repo.deleteById(id); }
}