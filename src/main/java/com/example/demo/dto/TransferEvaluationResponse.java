package com.example.demo.dto;

import java.util.List;

public class TransferEvaluationResponse {

    private Double totalTransferableCredits;
    private List<String> acceptedCourses;
    private List<String> missingCourses;
    private String status;
    private String remarks;

    public Double getTotalTransferableCredits() {
        return totalTransferableCredits;
    }
    public void setTotalTransferableCredits(Double totalTransferableCredits) {
        this.totalTransferableCredits = totalTransferableCredits;
    }

    public List<String> getAcceptedCourses() {
        return acceptedCourses;
    }
    public void setAcceptedCourses(List<String> acceptedCourses) {
        this.acceptedCourses = acceptedCourses;
    }

    public List<String> getMissingCourses() {
        return missingCourses;
    }
    public void setMissingCourses(List<String> missingCourses) {
        this.missingCourses = missingCourses;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
