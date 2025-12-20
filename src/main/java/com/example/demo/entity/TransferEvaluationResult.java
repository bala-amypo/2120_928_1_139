package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class TransferEvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Source course is required")
    @ManyToOne
    @JoinColumn(name = "source_course_id")
    private Course sourceCourse;

    @NotNull(message = "Target course is required")
    @ManyToOne
    @JoinColumn(name = "target_course_id")
    private Course targetCourse;

    @NotNull(message = "Eligibility must be specified")
    private Boolean eligible;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getSourceCourse() { return sourceCourse; }
    public void setSourceCourse(Course sourceCourse) {
        this.sourceCourse = sourceCourse;
    }

    public Course getTargetCourse() { return targetCourse; }
    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    public Boolean getEligible() { return eligible; }
    public void setEligible(Boolean eligible) { this.eligible = eligible; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
