package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.TransferRule;


public interface TransferRuleService {
TransferRule save(TransferRule r);
TransferRule get(Long id);
List<TransferRule> getAll();
void delete(Long id);
}