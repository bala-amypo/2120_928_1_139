package com.example.demo.dto;

public class TransferRuleDTO {

    private Long id;
    private Integer minimumOverlapPercentage;
    private Integer creditHourTolerance;
    private boolean active;
    private Long sourceUniversityId;
    private Long targetUniversityId;

    public TransferRuleDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinimumOverlapPercentage() {
        return minimumOverlapPercentage;
    }

    public void setMinimumOverlapPercentage(Integer minimumOverlapPercentage) {
        this.minimumOverlapPercentage = minimumOverlapPercentage;
    }

    public Integer getCreditHourTolerance() {
        return creditHourTolerance;
    }

    public void setCreditHourTolerance(Integer creditHourTolerance) {
        this.creditHourTolerance = creditHourTolerance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getSourceUniversityId() {
        return sourceUniversityId;
    }

    public void setSourceUniversityId(Long sourceUniversityId) {
        this.sourceUniversityId = sourceUniversityId;
    }

    public Long getTargetUniversityId() {
        return targetUniversityId;
    }

    public void setTargetUniversityId(Long targetUniversityId) {
        this.targetUniversityId = targetUniversityId;
    }
}
