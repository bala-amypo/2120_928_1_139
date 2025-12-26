package com.example.demo.repository;

import com.example.demo.entity.TransferRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRuleRepository extends JpaRepository<TransferRule, Long> {

    List<TransferRule> findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
            Long sourceUniversityId,
            Long targetUniversityId
    );
}
