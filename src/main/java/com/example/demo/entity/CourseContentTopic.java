package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class CourseContentTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Topic name is required")
    @Size(max = 200, message = "Topic name cannot exceed 200 characters")
    private String topicName;

    @NotNull(message = "Weight percentage is required")
    @DecimalMin(value = "0.0", message = "Weight percentage cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Weight percentage cannot be more than 100")
    private Double weightPercentage;

    @NotNull(message = "Course is required")
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public Double getWeightPercentage() { return weightPercentage; }
    public void setWeightPercentage(Double weightPercentage) {
        this.weightPercentage = weightPercentage;
    }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
