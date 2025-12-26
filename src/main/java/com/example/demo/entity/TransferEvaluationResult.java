package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class TransferEvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isEligibleForTransfer;
    private Double overlapPercentage;
    private String notes;

    @ManyToOne
    private Course sourceCourse;

    @ManyToOne
    private Course targetCourse;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getIsEligibleForTransfer() { return isEligibleForTransfer; }
    public void setIsEligibleForTransfer(Boolean eligible) { this.isEligibleForTransfer = eligible; }

    public Double getOverlapPercentage() { return overlapPercentage; }
    public void setOverlapPercentage(Double overlapPercentage) { this.overlapPercentage = overlapPercentage; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Course getSourceCourse() { return sourceCourse; }
    public void setSourceCourse(Course sourceCourse) { this.sourceCourse = sourceCourse; }

    public Course getTargetCourse() { return targetCourse; }
    public void setTargetCourse(Course targetCourse) { this.targetCourse = targetCourse; }
}
