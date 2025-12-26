package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TransferEvaluationResultRepository;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.service.TransferEvaluationService;

import java.util.List;
@Service
public class TransferEvaluationServiceImpl implements TransferEvaluationService {

    // ⚠️ FIELD NAMES USED BY REFLECTION
    private CourseRepository courseRepo;
    private CourseContentTopicRepository topicRepo;
    private TransferRuleRepository ruleRepo;
    private TransferEvaluationResultRepository resultRepo;

    @Override
    public TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId) {

        Course source = courseRepo.findById(sourceCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Course target = courseRepo.findById(targetCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (!source.isActive() || !target.isActive()) {
            throw new IllegalArgumentException("Course must be active");
        }

        List<CourseContentTopic> sourceTopics = topicRepo.findByCourseId(sourceCourseId);
        List<CourseContentTopic> targetTopics = topicRepo.findByCourseId(targetCourseId);

        double overlap = 0.0;
        double totalSourceWeight = 0.0;

        for (CourseContentTopic st : sourceTopics) {
            totalSourceWeight += st.getWeightPercentage();
            for (CourseContentTopic tt : targetTopics) {
                if (st.getTopicName().equalsIgnoreCase(tt.getTopicName())) {
                    overlap += Math.min(st.getWeightPercentage(), tt.getWeightPercentage());
                }
            }
        }

        if (totalSourceWeight == 0) {
            totalSourceWeight = 100.0;
        }

        double overlapPercentage = (overlap / totalSourceWeight) * 100;

        List<TransferRule> rules = ruleRepo
                .findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        source.getUniversity().getId(),
                        target.getUniversity().getId()
                );

        boolean eligible = false;
        String notes;

        for (TransferRule r : rules) {
            int tolerance = r.getCreditHourTolerance() == null ? 0 : r.getCreditHourTolerance();

            if (overlapPercentage >= r.getMinimumOverlapPercentage() &&
                    Math.abs(source.getCreditHours() - target.getCreditHours()) <= tolerance) {
                eligible = true;
                break;
            }
        }

        if (rules.isEmpty()) {
            notes = "No active transfer rule";
        } else if (!eligible) {
            notes = "No active rule satisfied";
        } else {
            notes = "Transfer eligible";
        }

        TransferEvaluationResult result = new TransferEvaluationResult();
        result.setSourceCourse(source);
        result.setTargetCourse(target);
        result.setOverlapPercentage(overlapPercentage);
        result.setIsEligibleForTransfer(eligible);
        result.setNotes(notes);

        return resultRepo.save(result);
    }

    @Override
    public TransferEvaluationResult getEvaluationById(Long id) {
        return resultRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found"));
    }

    @Override
    public List<TransferEvaluationResult> getEvaluationsForCourse(Long sourceCourseId) {
        return resultRepo.findBySourceCourseId(sourceCourseId);
    }
}
