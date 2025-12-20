package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class TransferRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Source university is required")
    @ManyToOne
    @JoinColumn(name = "source_university_id")
    private University sourceUniversity;

    @NotNull(message = "Target university is required")
    @ManyToOne
    @JoinColumn(name = "target_university_id")
    private University targetUniversity;

    @NotNull(message = "Minimum overlap percentage is required")
    @Min(value = 0, message = "Minimum overlap cannot be less than 0")
    @Max(value = 100, message = "Minimum overlap cannot be more than 100")
    private Double minimumOverlapPercentage;

    @NotNull(message = "Active status must be provided")
    private Boolean active = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public University getSourceUniversity() { return sourceUniversity; }
    public void setSourceUniversity(University sourceUniversity) {
        this.sourceUniversity = sourceUniversity;
    }

    public University getTargetUniversity() { return targetUniversity; }
    public void setTargetUniversity(University targetUniversity) {
        this.targetUniversity = targetUniversity;
    }

    public Double getMinimumOverlapPercentage() { return minimumOverlapPercentage; }
    public void setMinimumOverlapPercentage(Double minimumOverlapPercentage) {
        this.minimumOverlapPercentage = minimumOverlapPercentage;
    }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
