package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.*;


public interface CourseContentTopicRepository extends JpaRepository<CourseContentTopic, Long> {}
public interface TransferRuleRepository extends JpaRepository<TransferRule, Long> {}
public interface TransferEvaluationResultRepository extends JpaRepository<TransferEvaluationResult, Long> {}
public interface UserRepository extends JpaRepository<User, Long> {}