package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(max = 200, message = "Course name cannot exceed 200 characters")
    private String courseName;

    @NotNull(message = "Credit hours are required")
    @Min(value = 1, message = "Credit hours must be at least 1")
    private Integer creditHours;

    @NotNull(message = "Active status must be provided")
    private Boolean active = true;

    @NotNull(message = "University is required")
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCreditHours() { return creditHours; }
    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) {
        this.university = university;
    }
}
