package com.example.demo.entity;


import jakarta.persistence.*;


@Entity
public class TransferRule {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@ManyToOne
private University sourceUniversity;


@ManyToOne
private University targetUniversity;


private Double minimumOverlapPercentage;
private Boolean active = true;


public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public University getSourceUniversity() { return sourceUniversity; }
public void setSourceUniversity(University sourceUniversity) { this.sourceUniversity = sourceUniversity; }
public University getTargetUniversity() { return targetUniversity; }
public void setTargetUniversity(University targetUniversity) { this.targetUniversity = targetUniversity; }
public Double getMinimumOverlapPercentage() { return minimumOverlapPercentage; }
public void setMinimumOverlapPercentage(Double minimumOverlapPercentage) { this.minimumOverlapPercentage = minimumOverlapPercentage; }
public Boolean getActive() { return active; }
public void setActive(Boolean active) { this.active = active; }
}