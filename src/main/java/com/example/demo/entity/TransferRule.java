package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class TransferRule {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private University sourceUniversity;

    @ManyToOne
    private University targetUniversity;

    private Double minimumOverlapPercentage;
    private Integer creditHourTolerance = 0;
    private boolean active = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public University getSourceUniversity() { return sourceUniversity; }
    public void setSourceUniversity(University sourceUniversity) { this.sourceUniversity = sourceUniversity; }

    public University getTargetUniversity() { return targetUniversity; }
    public void setTargetUniversity(University targetUniversity) { this.targetUniversity = targetUniversity; }

    public Double getMinimumOverlapPercentage() { return minimumOverlapPercentage; }
    public void setMinimumOverlapPercentage(Double minimumOverlapPercentage) { this.minimumOverlapPercentage = minimumOverlapPercentage; }

    public Integer getCreditHourTolerance() { return creditHourTolerance; }
    public void setCreditHourTolerance(Integer creditHourTolerance) { this.creditHourTolerance = creditHourTolerance; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
