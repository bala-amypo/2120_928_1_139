package com.example.demo.dto;

import java.util.List;

public class TransferEvaluationRequest {

    private Long sourceProgramId;
    private Long targetProgramId;
    private List<CompletedCourseDTO> completedCourses;

    public Long getSourceProgramId() {
        return sourceProgramId;
    }
    public void setSourceProgramId(Long sourceProgramId) {
        this.sourceProgramId = sourceProgramId;
    }

    public Long getTargetProgramId() {
        return targetProgramId;
    }
    public void setTargetProgramId(Long targetProgramId) {
        this.targetProgramId = targetProgramId;
    }

    public List<CompletedCourseDTO> getCompletedCourses() {
        return completedCourses;
    }
    public void setCompletedCourses(List<CompletedCourseDTO> completedCourses) {
        this.completedCourses = completedCourses;
    }

    // inner DTO
    public static class CompletedCourseDTO {
        private String code;
        private String grade;
        private Double credits;

        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }

        public String getGrade() {
            return grade;
        }
        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Double getCredits() {
            return credits;
        }
        public void setCredits(Double credits) {
            this.credits = credits;
        }
    }
}
