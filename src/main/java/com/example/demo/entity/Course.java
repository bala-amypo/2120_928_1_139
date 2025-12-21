package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    name = "course",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"university_id", "courseCode"}
    )
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String courseCode;

    @NotBlank
    private String courseName;

    @NotNull
    @Min(1)
    private Integer creditHours;

    private String description;

    private String department;

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCreditHours() { return creditHours; }
    public void setCreditHours(Integer creditHours) { this.creditHours = creditHours; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }
}
