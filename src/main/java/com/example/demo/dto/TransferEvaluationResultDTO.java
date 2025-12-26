package com.example.demo.dto;

public class TransferEvaluationResultDTO {

    private Long id;
    private Long sourceCourseId;
    private Long targetCourseId;
    private Double overlapPercentage;
    private Boolean eligibleForTransfer;
    private String notes;

    public TransferEvaluationResultDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceCourseId() {
        return sourceCourseId;
    }

    public void setSourceCourseId(Long sourceCourseId) {
        this.sourceCourseId = sourceCourseId;
    }

    public Long getTargetCourseId() {
        return targetCourseId;
    }

    public void setTargetCourseId(Long targetCourseId) {
        this.targetCourseId = targetCourseId;
    }

    public Double getOverlapPercentage() {
        return overlapPercentage;
    }

    public void setOverlapPercentage(Double overlapPercentage) {
        this.overlapPercentage = overlapPercentage;
    }

    public Boolean getEligibleForTransfer() {
        return eligibleForTransfer;
    }

    public void setEligibleForTransfer(Boolean eligibleForTransfer) {
        this.eligibleForTransfer = eligibleForTransfer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
