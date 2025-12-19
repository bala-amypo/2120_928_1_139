package com.example.demo.entity;


import jakarta.persistence.*;


@Entity
public class Course {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String courseName;
private Integer creditHours;
private Boolean active = true;


@ManyToOne
private University university;


public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getCourseName() { return courseName; }
public void setCourseName(String courseName) { this.courseName = courseName; }
public Integer getCreditHours() { return creditHours; }
public void setCreditHours(Integer creditHours) { this.creditHours = creditHours; }
public Boolean getActive() { return active; }
public void setActive(Boolean active) { this.active = active; }
public University getUniversity() { return university; }
public void setUniversity(University university) { this.university = university; }
}