package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TransferRule;

@Repository
public interface TransferRuleRepository extends JpaRepository<TransferRule, Long> {

    List<TransferRule> findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
            Long sourceUniversityId,
            Long targetUniversityId
    );
}
